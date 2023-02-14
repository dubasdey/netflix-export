package org.erc.netflix.export;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class ErrorDialog {
   
    public static void open(Shell shell,String error){
        MessageBox box = new MessageBox(shell, SWT.OK);
        box.setText("Error");
        box.setMessage(error);
        box.open();
    }
}
