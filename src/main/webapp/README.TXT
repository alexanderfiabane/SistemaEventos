
    mocca-framework 1.0.0-SNAPSHOT - build 66
--------------------------------------------------------------------------------

    A Responsive CSS framework

================================================================================
 1. CHANGELOG
================================================================================
  LEGEND:
  [+] New feature added.
  [-] Feature removed.
  [=] Feature enhancement.
  [v] Visual enhancement.
  [b] Bugfix.

  Version 1.0.0-SNAPSHOT - build 65
  ------------------------------------------------------------------------------
  [b] table-wrapper class now takes effect only on screen media (dont affect
      printing).

  Version 1.0.0-SNAPSHOT - build 64
  ------------------------------------------------------------------------------
  [b] clock() plugin ajax fixes and enhancements.

  Version 1.0.0-SNAPSHOT - build 63
  ------------------------------------------------------------------------------
  [+] Added new ajaxTable and ajaxPseuoTable classes to mark refresh triggers:
        .table-refresh = buttons >> click. inputs >> change
        .table-refresh-click = element click
        .table-refresh-change = element change
        .table-refresh-enter = element enter key up

  Version 1.0.0-SNAPSHOT - build 62
  ------------------------------------------------------------------------------
  [=] getExpressionValue() now can use ifUndefined text.
  [b] Fixed Null copy.

  Version 1.0.0-SNAPSHOT - build 61
  ------------------------------------------------------------------------------
  [+] Added new .sphere class.

  Version 1.0.0-SNAPSHOT - build 60
  ------------------------------------------------------------------------------
  [+] Added new clock() plugin.

  Version 1.0.0-SNAPSHOT - build 59
  ------------------------------------------------------------------------------
  [=] findDialog() enhancements.

  Version 1.0.0-SNAPSHOT - build 58
  ------------------------------------------------------------------------------
  [=] confirmDialog() now passes its caller as param[1] to postCreateContent().

  Version 1.0.0-SNAPSHOT - build 57
  ------------------------------------------------------------------------------
  [=] Enhanced dialog width in phone devices.

  Version 1.0.0-SNAPSHOT - build 56
  ------------------------------------------------------------------------------
  [b] Fixed Date copy.

  Version 1.0.0-SNAPSHOT - build 55
  ------------------------------------------------------------------------------
  [b] Fixed ifEmpty for array expressions.

  Version 1.0.0-SNAPSHOT - build 54
  ------------------------------------------------------------------------------
  [b] Fixed qtip 2.2.1 overlay not showing.

  Version 1.0.0-SNAPSHOT - build 53
  ------------------------------------------------------------------------------
  [b] Fixed getExpressionValue() changing original object values.

  Version 1.0.0-SNAPSHOT - build 52
  ------------------------------------------------------------------------------
  [b] Fixed ajax[Pseudo]Table() postAddLine() hook.

  Version 1.0.0-SNAPSHOT - build 51
  ------------------------------------------------------------------------------
  [=] qTip2 updated to v2.2.1.

  Version 1.0.0-SNAPSHOT - build 50
  ------------------------------------------------------------------------------
  [b] Fixed ajax[Pseudo]Table() externalParams as a function not returning
      params.

  Version 1.0.0-SNAPSHOT - build 49
  ------------------------------------------------------------------------------
  [=] New improved tableSorter.
  [+] Now it is possible to control/disable confirmDialog() content cloning.
  [+] Added styles for <sup>/<sub> elements.

  Version 1.0.0-SNAPSHOT - build 48
  ------------------------------------------------------------------------------
  [b] Fixed border-radius on Dropdown sub-menus.

  Version 1.0.0-SNAPSHOT - build 47
  ------------------------------------------------------------------------------
  [b] Plugin dateTimePicker() now pass cleanText by default.
  [v] Added style to focused buttons.

  Version 1.0.0-SNAPSHOT - build 46
  ------------------------------------------------------------------------------
  [+] New classes [theme-name]-glow and [theme-name]-highlight.
  [=] Enhanced css quality validation.

  Version 1.0.0-SNAPSHOT - build 45
  ------------------------------------------------------------------------------
  [=] now $(radios/checkboxes).[un]check() triggers its change() event.
  [=] now $(options).[un]select() triggers the parent select change() event.

  Version 1.0.0-SNAPSHOT - build 44
  ------------------------------------------------------------------------------
  [b] tableSorter() now skips animation if BrowserDetector is undefined too.

  Version 1.0.0-SNAPSHOT - build 43
  ------------------------------------------------------------------------------
  [+] added class print-only.
  [+] fixed table hovering getting bold.
  [+] fixed arrow bug on tableSorter.

  Version 1.0.0-SNAPSHOT - build 42
  ------------------------------------------------------------------------------
  [+] tableSorter() now skips animation on crappy browsers (fucking IE!).

  Version 1.0.0-SNAPSHOT - build 41
  ------------------------------------------------------------------------------
  [+] Added tableSorter() plugin.

  Version 1.0.0-SNAPSHOT - build 40
  ------------------------------------------------------------------------------
  [+] Added watermark-xx/transparency-xx classes to control opacity.

  Version 1.0.0-SNAPSHOT - build 39
  ------------------------------------------------------------------------------
  [+] Added $.DateUtils.toDate() to mocca-extensions.

  Version 1.0.0-SNAPSHOT - build 38
  ------------------------------------------------------------------------------
  [=] Added scrollable to ajaxTable.
  [b] Fixed title on wizard's error pager not showing up.

  Version 1.0.0-SNAPSHOT - build 37
  ------------------------------------------------------------------------------
  [+] Added wizard widget.
  [+] Added responsive class for buttons, making it 100% width when in cell
      phones.
  [=] Various jquery-extension enhancements.

  Version 1.0.0-SNAPSHOT - build 36
  ------------------------------------------------------------------------------
  [+] Added validation span on findDialog.

  Version 1.0.0-SNAPSHOT - build 34
  ------------------------------------------------------------------------------
  [+] Panel widgets init automatically on document.ready(). To init manually
      (in case you wanna change some default settings), add "manual-init" class
      and do the init "$('#mypanel').panel({opts...});" yourself.

  Version 1.0.0-SNAPSHOT - build 33
  ------------------------------------------------------------------------------
  [+] Added isDetached() function to jquery-extensions.
  [+] Added panel widget.
  [b] fixed fieldset conflicting with responsive table wrappers.

  Version 1.0.0-SNAPSHOT - build 32
  ------------------------------------------------------------------------------
  [+] Added support for responsive tables (scroll tecnique).

  Version 1.0.0-SNAPSHOT - build 31
  ------------------------------------------------------------------------------
  [+] Added support for 9 digits phone numbers on phone mask.

  Version 1.0.0-SNAPSHOT - build 30
  ------------------------------------------------------------------------------
  [b] Fixed disabled drop-down item not showing properly.

  Version 1.0.0-SNAPSHOT - build 29
  ------------------------------------------------------------------------------
  [+] Added onEnter(func) extension.

  Version 1.0.0-SNAPSHOT - build 28
  ------------------------------------------------------------------------------
  [+] Added some classes to standardize form-actions.

  Version 1.0.0-SNAPSHOT - build 27
  ------------------------------------------------------------------------------
  [+] Added auto-focus on the findDialog keyword.

  Version 1.0.0-SNAPSHOT - build 26
  ------------------------------------------------------------------------------
  [b] fixed problem on findDialog validation.

  Version 1.0.0-SNAPSHOT - build 25
  ------------------------------------------------------------------------------
  [b] fixed ajax[Pseudo]Table regression bug, where the postAddLines where being
      executed outside the async block.

  Version 1.0.0-SNAPSHOT - build 24
  ------------------------------------------------------------------------------
  [+] confirmDialog buttons 'clickFunction' now receives the element that
      triggered the dialog.
      results.
  [b] ajax[Pseudo]Table now executes the postAddLines hook even if the search
      returns no results.

  Version 1.0.0-SNAPSHOT - build 23
  ------------------------------------------------------------------------------
  [+] added integer mask.
  [=] The @{expression} now outputs arrays/collections.

  Version 1.0.0-SNAPSHOT - build 22
  ------------------------------------------------------------------------------
  [b] jQuery-mask plugin updated to 1.6.5.
  [+] Added money automask.

  Version 1.0.0-SNAPSHOT - build 21
  ------------------------------------------------------------------------------
  [b] Totally rewrite of the browser-detection script (now browser-detector.js).

  Version 1.0.0-SNAPSHOT - build 20
  ------------------------------------------------------------------------------
  [b] Enhanced browser-detection.

  Version 1.0.0-SNAPSHOT - build 19
  ------------------------------------------------------------------------------
  [b] Fixed browser-detection customizing notSupportedBrowsers.

  Version 1.0.0-SNAPSHOT - build 18
  ------------------------------------------------------------------------------
  [b] Fixed visible area on print media, where only the first page was getting
      visible/printed.

  Version 1.0.0-SNAPSHOT - build 17
  ------------------------------------------------------------------------------
  [=] Enhancements to the WidgetUtils.blockUI() options.
  [b] Fixed documentDialog plugin attachments.
  [+] New doc site started (WIP).

  Version 1.0.0-SNAPSHOT - build 16
  ------------------------------------------------------------------------------
  [+] Added classes to control print media: no-print, print-no-bg,
      print-white-bg, print-black-txt, print-bw, print-plain

  Version 1.0.0-SNAPSHOT - build 15
  ------------------------------------------------------------------------------
  [+] Added postCreateContent hook on confirmDialog.
  [b] confirmDialog now delete the content template from the document.

  Version 1.0.0-SNAPSHOT - build 14
  ------------------------------------------------------------------------------
  [+] Document dialog plugin merged.
  [+] Added qtip-dialog-small and qtip-dialog-large classes to control dialog
      widths.
  [b] Fixed confirmDialog's repositionDialog().

  Version 1.0.0-SNAPSHOT - build 13
  ------------------------------------------------------------------------------
  [b] Fixed bugs on unwrap() functions.
  [v] Enhancements to dimmed class.
  [v] Enhancements to label class.
  [+] Added dimmed-strong and dimmed-weak classes.

  Version 1.0.0-SNAPSHOT - build 12
  ------------------------------------------------------------------------------
  [b] Prevent click outside confirmDialog to close the modal.
  [=] jquery-extensions.js performance enhancements.
  [+] Added javadocs to the jquery-extensions.js file.
