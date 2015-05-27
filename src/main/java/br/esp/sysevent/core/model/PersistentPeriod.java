/*
 * Copyright (C) 2014 Marcius da Silva da Fonseca.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package br.esp.sysevent.core.model;

import com.javaleks.commons.util.CalendarUtils;
import com.javaleks.core.model.embeddable.Period;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Embeddable;

/**
 *
 * @author vbox
 */
@Embeddable
public class PersistentPeriod extends Period {



    /**
     * Default constructor.
     */
    public PersistentPeriod() {
    }

    /**
     * Constructor that defines the start and end dates.
     * <p>
     * @param start The start date. Can be a {@link Date} or a {@link Calendar}.
     * @param end The end date. Can be a {@link Date} or a {@link Calendar}.
     */
    public PersistentPeriod(final Object start, final Object end) {
        this.start = CalendarUtils.castToCalendar(start);
        this.end = CalendarUtils.castToCalendar(end);
    }

    /**
     * Sets the start of the period, in Date format.
     *
     * @param start The start of the period.
     */
    public void setStartDate(final Date start) {
        this.start = CalendarUtils.castToCalendar(start);
    }

    /**
     * Sets the end of the period, in Date format.
     *
     * @param end The end of the period.
     */
    public void setEndDate(final Date end) {
        this.end = CalendarUtils.castToCalendar(end);
    }
}
