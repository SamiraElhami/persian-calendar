package com.smilehightech;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;

import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseEvent;

public class PersianDate implements MouseListener{

	

    private Display display = null;
    private Date nowDate = null; //current date

    private String selectedDate = null; //selected date
    private Shell shell = null;
    private GridLayout gridLayout = null;
    private GridData gridData = null;

    private CLabel sunday = null;
    private CLabel monday = null;
    private CLabel tuesday = null;
    private CLabel wednesday = null;
    private CLabel thursday = null;
    private CLabel friday = null;
    private CLabel saturday = null;

    private Button yearUp = null;
    private Button yearNext = null;
    private Button monthUp = null;
    private Button monthNext = null;
    private CLabel nowLabel = null;

    private CLabel[] days = new CLabel[42];
    
    private ULocale locale = new ULocale("fa_IR@calendar=persian");
    private Calendar calendar = Calendar.getInstance(locale); //get current Calendar object
    
    
    public PersianDate(Shell parent, int style) {
     //   super(parent, style);
    }

    public PersianDate(Shell parent) {
        this(parent, 0);
    }

    private int getLastDayOfMonth(int year, int month) {
        if (month == 1 ||
            month == 2 ||
            month == 3 ||
            month == 4 ||
            month == 5 ||
            month == 6 ) {
            return 31;
        }
        if (month == 7 ||
            month == 8 ||
            month == 9 ||
            month == 10 ||
            month == 11 ) {
            return 30;
        }
        if (month == 12) {
            if (isLeapYear(year)) {
                return 29;
            } else {
                return 28;
            }
        }
        return 0;
    }

    public boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    private void moveTo(int type, int value) {
        Calendar now = Calendar.getInstance(locale); //get current Calendar object
        now.setTime(nowDate); //set current date
        now.add(type, value); //add to spec time.
        nowDate = new Date(now.getTimeInMillis()); //result
//        SimpleDateFormat formatter = new 
//        		SimpleDateFormat("yyyy/MM");//format date
        int month= now.get(calendar.MONTH)+1;
        int year = now.get(calendar.YEAR);
        if(month <10){
        	nowLabel.setText(Integer.toString(year) + "-0" + Integer.toString(month));
        }else{
        	nowLabel.setText(Integer.toString(year) + "-" + Integer.toString(month));
	        
        }

        //nowLabel.setText(formatter.format(nowDate)); //set to label
        setDayForDisplay(now);
    }

    private void setDayForDisplay(Calendar now) {
    	//Calendar calendar = Calendar.getInstance(locale); //get current Calendar object
        
        int currentDay = now.get(calendar.DATE);
        now.add(calendar.DAY_OF_MONTH, -(now.get(calendar.DATE) - 1)); //
        int startIndex = now.get(calendar.DAY_OF_WEEK); //
        int year = now.get(calendar.YEAR); //
        int month = now.get(calendar.MONTH) + 1; //
        int lastDay = this.getLastDayOfMonth(year, month); //
        int endIndex = startIndex + lastDay - 1; //
        int startday = 1;
        for (int i = 0; i < 42; i++) {
            Color temp = days[i].getBackground();
            if (temp.equals(display.getSystemColor(SWT.COLOR_BLUE))) {
                
days[i].setBackground(display.getSystemColor(SWT.COLOR_WHITE));
            }
        }
        for (int i = 0; i < 42; i++) {
            if (i >= startIndex && i <= endIndex) {
                days[i].setText("" + startday);
                if (startday == currentDay) {
                    
days[i].setBackground(display.getSystemColor(SWT.COLOR_BLUE)); //
                }
                startday++;
            } else {
                days[i].setText("");
            }
        }

    }

    public void previousYear() {
        moveTo(calendar.YEAR, -1);
    }

    public void nextYear() {
        moveTo(calendar.YEAR, 1);
    }

    public void nextMonth() {
        moveTo(calendar.MONTH, 1);
    }

    public void previousMonth() {
        moveTo(calendar.MONTH, -1);
    }

    public void mouseDoubleClick(MouseEvent e) {
        CLabel day = (CLabel) e.getSource();
        if(!day.getText().equals(""))
        {
            this.selectedDate = nowLabel.getText() + "-" + day.getText();
            this.shell.close();
        }
    }

    public void mouseDown(MouseEvent e) {}

    public void mouseUp(MouseEvent e) {}


    public Object open() {
        Shell parent = new Shell();
        display = Display.getDefault();
       // SWT.PRIMARY_MODAL
        shell = new Shell(parent,  SWT.TITLE  | SWT.APPLICATION_MODAL);
        shell.setText("تقویم");
        shell.setSize(270, 220);
        shell.setOrientation(SWT.RIGHT_TO_LEFT);
        gridLayout = new GridLayout();
        gridLayout.numColumns = 7;
        shell.setLayout(gridLayout);

        gridData = new GridData(GridData.FILL_HORIZONTAL);
        yearUp = new Button(shell, SWT.PUSH | SWT.FLAT);
        yearUp.setText("<");
        yearUp.setLayoutData(gridData);
        yearUp.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                previousYear();
            }
        });

        gridData = new GridData(GridData.FILL_HORIZONTAL);
        monthUp = new Button(shell, SWT.PUSH | SWT.FLAT);
        monthUp.setText("<<");
        monthUp.setLayoutData(gridData);
        monthUp.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                previousMonth();
            }
        });

        nowLabel = new CLabel(shell, SWT.CENTER | SWT.SHADOW_OUT);
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = 3;
        nowLabel.setLayoutData(gridData);
        
        int month= calendar.get(calendar.MONTH)+1;
        int year = calendar.get(calendar.YEAR);
        if(month <10){
        	nowLabel.setText(Integer.toString(year) + "-0" + Integer.toString(month));
        }else{
        	nowLabel.setText(Integer.toString(year) + "-" + Integer.toString(month));
	        
        }

        gridData = new GridData(GridData.FILL_HORIZONTAL);
        monthNext = new Button(shell, SWT.PUSH | SWT.FLAT);
        monthNext.setText(">>");
        monthNext.setLayoutData(gridData);
        monthNext.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                nextMonth();
            }
        });

        gridData = new GridData(GridData.FILL_HORIZONTAL);
        yearNext = new Button(shell, SWT.PUSH | SWT.FLAT);
        yearNext.setText(">");
        yearNext.setLayoutData(gridData);
        yearNext.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                nextYear();
            }
        });
        
        saturday = new CLabel(shell, SWT.CENTER | SWT.SHADOW_OUT);
        gridData = new GridData(GridData.FILL_HORIZONTAL |
                                GridData.FILL_VERTICAL);
        gridData.widthHint = 35;
        gridData.heightHint = 20;
        saturday.setLayoutData(gridData);
        saturday.setText("ش");

        sunday = new CLabel(shell, SWT.CENTER | SWT.SHADOW_OUT);
        gridData = new GridData(GridData.FILL_HORIZONTAL |
                                GridData.FILL_VERTICAL);
        gridData.widthHint = 35;
        gridData.heightHint = 20;
        sunday.setLayoutData(gridData);
        sunday.setText("ی");

        monday = new CLabel(shell, SWT.CENTER | SWT.SHADOW_OUT);
        gridData = new GridData(GridData.FILL_HORIZONTAL |
                                GridData.FILL_VERTICAL);
        gridData.widthHint = 35;
        gridData.heightHint = 20;
        monday.setLayoutData(gridData);
        monday.setText("د");

        tuesday = new CLabel(shell, SWT.CENTER | SWT.SHADOW_OUT);
        gridData = new GridData(GridData.FILL_HORIZONTAL |
                                GridData.FILL_VERTICAL);
        gridData.widthHint = 35;
        gridData.heightHint = 20;
        tuesday.setLayoutData(gridData);
        tuesday.setText("س");

        wednesday = new CLabel(shell, SWT.CENTER | SWT.SHADOW_OUT);
        gridData = new GridData(GridData.FILL_HORIZONTAL |
                                GridData.FILL_VERTICAL);
        gridData.widthHint = 35;
        gridData.heightHint = 20;
        wednesday.setLayoutData(gridData);
        wednesday.setText("چ");

        thursday = new CLabel(shell, SWT.CENTER | SWT.SHADOW_OUT);
        gridData = new GridData(GridData.FILL_HORIZONTAL |
                                GridData.FILL_VERTICAL);
        gridData.widthHint = 35;
        gridData.heightHint = 20;
        thursday.setLayoutData(gridData);
        thursday.setText("پ");

        friday = new CLabel(shell, SWT.CENTER | SWT.SHADOW_OUT);
        gridData = new GridData(GridData.FILL_HORIZONTAL |
                                GridData.FILL_VERTICAL);
        gridData.widthHint = 35;
        gridData.heightHint = 20;
        friday.setLayoutData(gridData);
        friday.setText("ج");

       
        for (int i = 0; i < 42; i++) {
            days[i] = new CLabel(shell, SWT.FLAT | SWT.CENTER);
            gridData = new GridData(GridData.FILL_HORIZONTAL |
                                    GridData.FILL_VERTICAL);
            days[i].setLayoutData(gridData);
            days[i].setBackground(display.getSystemColor(SWT.COLOR_WHITE));
            days[i].addMouseListener( this);
            days[i].setToolTipText("double click get current date.");
        }

        Calendar now = Calendar.getInstance(locale); //
        nowDate = new Date(now.getTimeInMillis());
        setDayForDisplay(now);

        shell.open();
        Display display = parent.getDisplay();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        return selectedDate;
    }
    
    public void dispose() {
    	this.dispose();
    }
    

    /**
     * @param args
     */
    public static void main(String[] args) {
        Display display = new Display();
        final Shell shell = new Shell(display);
        shell.setText("تقویم");

        FillLayout fl = new FillLayout();
        shell.setLayout(fl);
        Button open = new Button(shell, SWT.PUSH);
        open.setText("open");
        open.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
              PersianDate cd = new PersianDate(shell);
                System.out.println(cd.open());
            }
        });
        shell.pack();
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        
    }
}


}
