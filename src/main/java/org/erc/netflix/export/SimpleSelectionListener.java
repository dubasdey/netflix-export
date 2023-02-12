package org.erc.netflix.export;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

public abstract class SimpleSelectionListener implements SelectionListener{


    public abstract void select(SelectionEvent e);

    @Override
    public void widgetDefaultSelected(SelectionEvent e) {
        this.select(e);
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
        this.select(e);
    }
}
