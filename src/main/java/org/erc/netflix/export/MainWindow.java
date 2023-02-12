package org.erc.netflix.export;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class MainWindow {
    
    /**
     *
     */
    private static final String NETFLIX_START = "https://www.netflix.com/es/login";

    /**
     *
     */
    private static final String NETFLIX_LIST = "https://www.netflix.com/browse/my-list";

    private Display display;

    private Shell shell;

    private Browser browser;

    private Label statusLine;

    private Config config = new Config();

    private void createMenuBar(){
        // Menu
        Menu menuBar = new Menu(shell, SWT.BAR);
    
        /// File Menu
        MenuItem fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
        fileMenuHeader.setText("&File");
        Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
        fileMenuHeader.setMenu(fileMenu);
        MenuItem fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
        fileExitItem.setText("E&xit");

        // Export Menu
        MenuItem exportMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
        exportMenuHeader.setText("&Export");
        Menu exportMenu = new Menu(shell, SWT.DROP_DOWN);
        exportMenuHeader.setMenu(exportMenu);
        MenuItem exportMenuItem = new MenuItem(exportMenu, SWT.PUSH);
        exportMenuItem.setText("Export &List");

        // Exit action
        fileExitItem.addSelectionListener(new SimpleSelectionListener(){
            @Override
            public void select(SelectionEvent e) {
                shell.close();
                display.dispose();
            }
        });
    
        // Export Menu
        exportMenuItem.addSelectionListener(new SimpleSelectionListener(){
            @Override
            public void select(SelectionEvent e) {
                browser.setUrl(config.get("netflix-list",NETFLIX_LIST));
            }
        });

        shell.setMenuBar(menuBar);
    }

    private void createStatusBar(){
        statusLine = new Label(shell, SWT.NONE);
        statusLine.setText("");
        statusLine.setLayoutData(new GridData(GridData.FILL, GridData.END, true, false));
    }
    
    private void createBrowser(){
        browser = new Browser( shell, SWT.EDGE );
        browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        browser.addProgressListener(new ProgressListener() {
            @Override
            public void changed(ProgressEvent event) {
                statusLine.setText(String.format("(Loading) %s", browser.getUrl()));
            }
            @Override
            public void completed(ProgressEvent event) {
                statusLine.setText(String.format("%s",browser.getUrl()));
                String listUrl = config.get("netflix-list",NETFLIX_LIST);
                if(browser.getUrl().equalsIgnoreCase(listUrl)){
                    try{
                        ExportParser parser = new ExportParser();
                        statusLine.setText("Reading list...");
                        String allPageContent = browser.getText();
                        String content = parser.parse(allPageContent);
                        if(content!=null){
                            java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                                new java.awt.datatransfer.StringSelection(content),
                                null
                            ); 
                            statusLine.setText("List copied to clipboard");
                        }else{
                            statusLine.setText("Not valid list generated");
                        }
                    }catch(Exception e){
                        statusLine.setText("Not valid list generated");
                    }
                }
            }
        });
    }


    public void open(){
        display = new Display();

        shell = new Shell(display, SWT.RESIZE | SWT.MAX | SWT.MIN);
        shell.setText("Netflix Export");
        shell.setMinimumSize(800, 600);
        shell.setMaximized(true);
        shell.setLayout(new GridLayout(1,false));
    
        createMenuBar();
        
        createBrowser();

        createStatusBar();

        shell.open();
        browser.setUrl(config.get("netflix-start",NETFLIX_START));

        while (!shell.isDisposed()) {
          if (!display.readAndDispatch())
            display.sleep();
        }
        display.dispose();
    }
}
