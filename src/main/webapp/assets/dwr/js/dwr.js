/*
 * Copyright (c) 2011, CPD-UFSM. All Rights Reserved.
 */
var dwrCallCount = 0;
function preHook(img) {
    dwr.engine.setPreHook(function() {
        dwrCallCount ++;
        if (!YAHOO.container.wait) {
            YAHOO.container.wait = new YAHOO.widget.Panel("wait",
            {
                fixedcenter: true,
                close: false,
                draggable: false,
                zindex:4,
                modal: true,
                visible: false
            });

            YAHOO.container.wait.setHeader("Aguarde...");
            YAHOO.container.wait.setBody(img);
            YAHOO.container.wait.render(document.body);
        }
        YAHOO.container.wait.show();
    });
}
function postHook() {
    dwr.engine.setPostHook(function() {
        dwrCallCount --;
        if(dwrCallCount == 0) {
            YAHOO.container.wait.hide();
        }
    });
}


