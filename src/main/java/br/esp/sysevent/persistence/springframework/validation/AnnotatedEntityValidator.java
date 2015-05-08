/*
 * commons-persistence - Copyright (c) 2009-2013 MSF. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to
 * the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */
package br.esp.sysevent.persistence.springframework.validation;

import static br.esp.sysevent.persistence.springframework.validation.ValidationUtils.getFullPath;
import static br.esp.sysevent.persistence.springframework.validation.ValidationUtils.isNotNullCalendar;
import static br.esp.sysevent.persistence.springframework.validation.ValidationUtils.isNotNullCharSequence;
import static br.esp.sysevent.persistence.springframework.validation.ValidationUtils.isNotNullEntity;
import static br.esp.sysevent.persistence.springframework.validation.ValidationUtils.isNullOrEmpty;
import br.esp.sysevent.persistence.springframework.validation.annotations.MaxLength;
import br.esp.sysevent.persistence.springframework.validation.annotations.MaxValue;
import br.esp.sysevent.persistence.springframework.validation.annotations.MinLength;
import br.esp.sysevent.persistence.springframework.validation.annotations.MinValue;
import br.esp.sysevent.persistence.springframework.validation.annotations.Regex;
import br.esp.sysevent.persistence.springframework.validation.annotations.Required;
import br.esp.sysevent.persistence.springframework.validation.annotations.SkipSubvalidation;
import br.esp.sysevent.persistence.springframework.validation.annotations.SkipValidation;
import br.esp.sysevent.persistence.springframework.validation.annotations.Unique;
import com.javaleks.commons.core.dao.EntityDao;
import com.javaleks.commons.core.model.Entity;
import com.javaleks.commons.util.CalendarUtils;
import com.javaleks.commons.util.CharSequenceUtils;
import com.javaleks.commons.util.CollectionUtils;
import com.javaleks.commons.util.ReflectionUtils;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Calendar;
import java.util.Collection;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.validation.Errors;


/**
 * TODO : Describe.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public abstract class AnnotatedEntityValidator<ID extends Serializable & Comparable<ID>, T extends Entity<ID>> extends AbstractValidator<T> {

    public AnnotatedEntityValidator() {
        this.commandClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @Override
    public final void validateCommand(final T target, final Errors errors) {
        validateCommand("", target, errors);
    }

    public void complementarValidation(final String pathPrefix, final T target, final Errors errors) {
    }

    protected EntityDao<ID, T> getCommandService() {
        return null;
    }

    protected AnnotatedEntityValidator getValidatorFor(final Class<? extends Entity> entityClass) {
        return null;
    }

    protected final void validateCommand(final String pathPrefix, final T target, final Errors errors) {
        try {
            final Collection<Field> fields = ReflectionUtils.getFields(target, false, false, false);
            for (Field field : fields) {
                if (isSkipValidation(field)) {
                    continue;
                }
                validateRequired(pathPrefix, field, target, errors);
                validateRegex(pathPrefix, field, target, errors);
                validateMinLength(pathPrefix, field, target, errors);
                validateMaxLength(pathPrefix, field, target, errors);
                validateUnique(pathPrefix, field, target, errors);
                doSubvalidation(pathPrefix, field, target, errors);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        complementarValidation(pathPrefix, target, errors);
    }

    private void extraValidations(String fieldPath, Field field, T target, Errors errors) throws Exception {
        final Object fieldValue = field.get(target);
        final MinValue minValue = field.getAnnotation(MinValue.class);
        final MaxValue maxValue = field.getAnnotation(MaxValue.class);
        if (isNotNullCharSequence(fieldValue)) {
            final CharSequence val = (CharSequence) fieldValue;
            if (minValue != null && CharSequenceUtils.compare(val, minValue.value()) < 0) {
                errors.rejectValue(fieldPath, minValue.errorCode(), null, minValue.errorCode());
                return;
            }
            if (maxValue != null && CharSequenceUtils.compare(val, maxValue.value()) > 0) {
                errors.rejectValue(fieldPath, maxValue.errorCode(), null, maxValue.errorCode());
            }
        } else if (isNotNullCalendar(fieldValue)) {
            final Calendar c1 = (Calendar) fieldValue;
            if (minValue != null) {
                final Calendar c2 = CalendarUtils.parse(minValue.value(), minValue.pattern());
                if (c1.compareTo(c2) < 0) {
                    errors.rejectValue(fieldPath, minValue.errorCode(), new Object[]{minValue.value()}, minValue.errorCode());
                    return;
                }
            }
            if (maxValue != null) {
                final Calendar c2 = CalendarUtils.parse(maxValue.value(), maxValue.pattern());
                if (c1.compareTo(c2) > 0) {
                    errors.rejectValue(fieldPath, maxValue.errorCode(), new Object[]{maxValue.value()}, maxValue.errorCode());
                }
            }
        }

    }

    /**/
    private boolean isSkipValidation(final Field field) throws Exception {
        final SkipValidation skip = field.getAnnotation(SkipValidation.class);
        return skip != null && skip.value();
    }

    private void validateRequired(final String pathPrefix, final Field field, final T target, final Errors errors) throws Exception {
        field.setAccessible(true);
        final Object fieldValue = field.get(target);
        final String fieldPath = getFullPath(pathPrefix, field.getName());

        final Required required = field.getAnnotation(Required.class);
        final Column column = field.getAnnotation(Column.class);
        final JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
        final String errorCode = required != null ? required.errorCode() : Validator.ERROR_REQUIRED;
        final boolean isRequired = (required != null && required.value()) || (column != null && !column.nullable()) || (joinColumn != null && !joinColumn.nullable());

        if (isRequired && isNullOrEmpty(fieldValue)) {
            errors.rejectValue(fieldPath, errorCode, null, errorCode);
        }
    }

    private void validateUnique(final String pathPrefix, final Field field, final T target, final Errors errors) throws Exception {
        field.setAccessible(true);
        final Object fieldValue = field.get(target);
        final String fieldPath = getFullPath(pathPrefix, field.getName());
        if (!errors.hasFieldErrors(fieldPath)) {
            final Unique unique = field.getAnnotation(Unique.class);
            final Column column = field.getAnnotation(Column.class);
            final JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
            final String errorCode = unique != null ? unique.errorCode() : Validator.ERROR_EXISTS;
            final boolean isUnique = (unique != null && unique.value()) || (column != null && column.unique()) || (joinColumn != null && joinColumn.unique());
            if (isUnique && getCommandService() != null) {
                Collection<T> found = getCommandService().findByProperty(field.getName(), fieldValue);
                if (!CollectionUtils.isEmptyOrSingleton(found)) {
                    throw new IllegalStateException("Inconsistent database: expecting unique result. Returned mutiple.");
                }
                final T entity = DataAccessUtils.uniqueResult(found);

                if (entity != null && (target.getId() == null || !entity.getId().equals(target.getId()))) {
                    errors.rejectValue(fieldPath, errorCode, new Object[]{field.getName(), fieldValue}, errorCode);
                }
            }
        }
    }

    private void validateRegex(final String pathPrefix, final Field field, final T target, final Errors errors) throws Exception {
        field.setAccessible(true);
        final Object fieldValue = field.get(target);
        final String fieldPath = getFullPath(pathPrefix, field.getName());
        if (!errors.hasFieldErrors(fieldPath) && isNotNullCharSequence(fieldValue)) {
            final Regex regex = field.getAnnotation(Regex.class);
            final CharSequence val = (CharSequence) fieldValue;
            if (regex != null && !Pattern.compile(regex.value(), regex.flags()).matcher(val).matches()) {
                errors.rejectValue(fieldPath, regex.errorCode(), null, regex.errorCode());
            }
        }
    }

    private void validateMinLength(final String pathPrefix, final Field field, final T target, final Errors errors) throws Exception {
        field.setAccessible(true);
        final Object fieldValue = field.get(target);
        final String fieldPath = getFullPath(pathPrefix, field.getName());
        if (!errors.hasFieldErrors(fieldPath) && isNotNullCharSequence(fieldValue)) {
            final MinLength minLength = field.getAnnotation(MinLength.class);
            final CharSequence val = (CharSequence) fieldValue;
            if (minLength != null && val.length() < minLength.value()) {
                errors.rejectValue(fieldPath, minLength.errorCode(), new Object[]{minLength.value()}, minLength.errorCode());
            }
        }
    }

    private void validateMaxLength(final String pathPrefix, final Field field, final T target, final Errors errors) throws Exception {
        field.setAccessible(true);
        final Object fieldValue = field.get(target);
        final String fieldPath = getFullPath(pathPrefix, field.getName());
        if (!errors.hasFieldErrors(fieldPath) && isNotNullCharSequence(fieldValue)) {
            final MaxLength maxLength = field.getAnnotation(MaxLength.class);
            final Column column = field.getAnnotation(Column.class);
            final String errorCode = maxLength != null ? maxLength.errorCode() : Validator.ERROR_MAX_LENGTH;
            if (maxLength != null || column != null) {
                int max = 255;
                if (maxLength != null && column != null) {
                    max = maxLength.value() < column.length() ? maxLength.value() : column.length();
                } else if (maxLength != null) {
                    max = maxLength.value();
                } else if (column != null) {
                    max = column.length();
                }
                final CharSequence val = (CharSequence) fieldValue;
                if (val.length() > max) {
                    errors.rejectValue(fieldPath, errorCode, new Object[]{max}, errorCode);
                }
            }
        }
    }

    private void doSubvalidation(final String pathPrefix, final Field field, final T target, final Errors errors) throws Exception {
        field.setAccessible(true);
        final Object fieldValue = field.get(target);
        final String fieldPath = getFullPath(pathPrefix, field.getName());
        if (!errors.hasFieldErrors(fieldPath) && isNotNullEntity(fieldValue)) {
            final JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
            final SkipSubvalidation skip = field.getAnnotation(SkipSubvalidation.class);
            final boolean isSkip = (skip != null && skip.value());

            if (!isSkip && joinColumn != null) {
                final Entity entityValue = ((Entity) fieldValue);
                final AnnotatedEntityValidator v = getValidatorFor(entityValue.getClass());
                if (v != null) {
                    v.validateCommand(fieldPath, entityValue, errors);
                }
            }
        }
    }
}
