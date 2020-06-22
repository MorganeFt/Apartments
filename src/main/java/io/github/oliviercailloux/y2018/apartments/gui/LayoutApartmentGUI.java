package io.github.oliviercailloux.y2018.apartments.gui;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.apartment.json.JsonConvert;
import io.github.oliviercailloux.y2018.apartments.valuefunction.LinearAVF;
import java.util.ArrayList;
import org.eclipse.swt.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author AlWAZZAN & SAKHO */
/** this class displays a list of apartments sorted according to the user's utilities */
public class LayoutApartmentGUI {

  private static final Logger LOGGER = LoggerFactory.getLogger(LayoutApartmentGUI.class);

  java.util.List<Apartment> listApp;
  LinearAVF linearAVF;

  Display display = new Display();
  Shell shell = new Shell(display);

  /** add objects of apartments in a listShell */
  final List listShell = new List(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);

  public LayoutApartmentGUI(LinearAVF newLinearAVF) {
    this.linearAVF = newLinearAVF;
    this.listApp = getListSorted(linearAVF);
  }

  /** General method which displays all the sorted apartment */
  public void displayAppart() {

    addAppinListShell();

    shell.setText("Filtered results");

    // create a gridLayout of 3 columns
    GridLayout gridLayout = new GridLayout();
    gridLayout.numColumns = 3;
    shell.setLayout(gridLayout);

    // create a griddata for a list of objects
    GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
    gridData.horizontalSpan = 2;

    // create a list label
    Label label = new Label(shell, SWT.NULL);
    label.setText("List of availabal apartments :");
    label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));

    // define griddata with a verticalspan : 3 rows
    gridData = new GridData(GridData.FILL_BOTH);
    gridData.verticalSpan = 3;

    gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL);
    gridData.verticalSpan = 4;
    gridData.heightHint = 400;
    listShell.setLayoutData(gridData);

    Group appartInfo = new Group(shell, SWT.NULL);
    appartInfo.setText("Detals on selected apartment :");

    gridLayout = new GridLayout();
    gridLayout.numColumns = 2;
    appartInfo.setLayout(gridLayout);

    gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
    gridData.horizontalSpan = 2;
    appartInfo.setLayoutData(gridData);

    // beginning of creation of elements to display when we click on an apartment
    Label adresse;
    Label surface;
    Label prix;
    Label nbrChambres;
    Label wifi;
    Label tv;
    Label terrace;
    Label nbBathroom;

    Label wifiLabel;
    Label tvLabel;
    Label terraceLabel;
    Label nbBathroomLabel;

    GridData dataLabelAddress = new GridData(GridData.FILL_HORIZONTAL);
    dataLabelAddress.widthHint = 250;
    dataLabelAddress.heightHint = 45;

    GridData dataLabel = new GridData(GridData.FILL_HORIZONTAL);
    dataLabel.widthHint = 250;

    GridData dataLabelHide = new GridData(GridData.FILL_HORIZONTAL);
    dataLabel.widthHint = 250;

    new Label(appartInfo, SWT.NULL).setText("Address :");
    adresse = new Label(appartInfo, SWT.SINGLE | SWT.BORDER);
    adresse.setLayoutData(dataLabelAddress);

    new Label(appartInfo, SWT.NULL).setText("Surface :");
    surface = new Label(appartInfo, SWT.SINGLE | SWT.BORDER);
    surface.setLayoutData(dataLabel);

    new Label(appartInfo, SWT.NULL).setText("Price :");
    prix = new Label(appartInfo, SWT.SINGLE | SWT.BORDER);
    prix.setLayoutData(dataLabel);

    new Label(appartInfo, SWT.NULL).setText("Number of bedrooms :");
    nbrChambres = new Label(appartInfo, SWT.SINGLE | SWT.BORDER);
    nbrChambres.setLayoutData(dataLabel);

    nbBathroomLabel = new Label(appartInfo, SWT.NULL);
    nbBathroomLabel.setText("Number of bathrooms :");
    nbBathroomLabel.setVisible(false);
    nbBathroom = new Label(appartInfo, SWT.SINGLE | SWT.BORDER);
    nbBathroom.setLayoutData(dataLabelHide);
    nbBathroom.setVisible(false);

    wifiLabel = new Label(appartInfo, SWT.NULL);
    wifiLabel.setText("Wifi :");
    wifiLabel.setVisible(false);
    wifi = new Label(appartInfo, SWT.SINGLE | SWT.BORDER);
    wifi.setLayoutData(dataLabelHide);
    wifi.setVisible(false);

    tvLabel = new Label(appartInfo, SWT.NULL);
    tvLabel.setText("Tv :");
    tvLabel.setVisible(false);
    tv = new Label(appartInfo, SWT.SINGLE | SWT.BORDER);
    tv.setLayoutData(dataLabelHide);
    tv.setVisible(false);

    terraceLabel = new Label(appartInfo, SWT.NULL);
    terraceLabel.setText("Terrace :");
    terraceLabel.setVisible(false);
    terrace = new Label(appartInfo, SWT.SINGLE | SWT.BORDER);
    terrace.setLayoutData(dataLabelHide);
    terrace.setVisible(false);

    gridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
    gridData.horizontalSpan = 3;
    onClick(adresse, surface, prix, nbrChambres, nbBathroom, wifi, tv, terrace);

    Button button = new Button(shell, SWT.NONE);
    button.setText("View more");
    button.setSize(100, 25);
    button.addListener(
        SWT.Selection,
        new Listener() {
          @Override
          public void handleEvent(Event event) {
            if (button.getText().equals("View more")) {
              nbBathroomLabel.setVisible(true);
              wifiLabel.setVisible(true);
              tvLabel.setVisible(true);
              terraceLabel.setVisible(true);
              nbBathroom.setVisible(true);
              wifi.setVisible(true);
              tv.setVisible(true);
              terrace.setVisible(true);

              dataLabelHide.exclude = false;
              appartInfo.pack();

              button.setText("Hide fields");
            } else {
              nbBathroomLabel.setVisible(false);
              wifiLabel.setVisible(false);
              tvLabel.setVisible(false);
              terraceLabel.setVisible(false);
              nbBathroom.setVisible(false);
              wifi.setVisible(false);
              tv.setVisible(false);
              terrace.setVisible(false);

              dataLabelHide.exclude = true;

              button.setText("View more");
            }
          }
        });

    shell.setSize(1180, 550);
    shell.open();
    LOGGER.info("The Shell was opened with success.");

    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    LOGGER.info("The screen was closed with success.");
  }

  /**
   * Method that creates a list of random apartments and tries them according to the utility of the
   * user
   *
   * @param avf a way to rate the apartments
   */
  private static java.util.List<Apartment> getListSorted(LinearAVF linearAVF) {
    java.util.List<Apartment> listApartment = JsonConvert.getDefaultApartments();
    java.util.List<Apartment> appart = new ArrayList<>();
    //    java.util.List<Integer> randomList = new ArrayList<>();
    //    Random ran = new Random();
    //    int newRandom = ran.nextInt(499);

    for (int i = 0; i < 49; i++) {
      //    	FOR TEST KEEPING THE SAME 50 APART

      //      while (randomList.indexOf(newRandom) != -1) {
      //        newRandom = ran.nextInt(499);
      //      }
      //      randomList.add(newRandom);
      //      appart.add(listApartment.get(randomList.get(newRandom)));
      appart.add(listApartment.get(i));
    }

    appart.sort(
        (Apartment c, Apartment d) -> {
          return -Double.compare(linearAVF.getSubjectiveValue(c), linearAVF.getSubjectiveValue(d));
        });

    return appart;
  }

  /** Method that adds available apartments in the shell list to display */
  public void addAppinListShell() {
    for (Apartment a : listApp) {
      listShell.add("Title: " + a.getTitle() + "\t" + " Address : " + a.getAddress());
    }
  }

  /**
   * Method that defines the action to execute when clicking on an apartment, here we display some
   * elements of the apartment
   *
   * @param listApp3 the list of apartments to display
   * @param address, surface, price, number of bedrooms the parameters of apps to display when
   *     clicking on an apartment
   */
  private void onClick(
      Label adresse,
      Label surface,
      Label prix,
      Label nbrChambres,
      Label nbBathroom,
      Label wifi,
      Label tv,
      Label terrace) {
    // the listener when we click on an apartment
    SelectionAdapter selectApp =
        new SelectionAdapter() {
          @Override
          public void widgetSelected(SelectionEvent event) {
            int[] selectedItems = listShell.getSelectionIndices();

            for (int loopIndex = 0; loopIndex < selectedItems.length; loopIndex++) {
              adresse.setText(
                  listApp.get(listShell.getSelectionIndex()).getAddress().replace(", ", "\n"));
              surface.setText(
                  " "
                      + Math.round(listApp.get(listShell.getSelectionIndex()).getFloorArea())
                      + "m²");
              prix.setText(
                  " "
                      + Math.round(listApp.get(listShell.getSelectionIndex()).getPricePerNight())
                      + "€");
              nbrChambres.setText(" " + listApp.get(listShell.getSelectionIndex()).getNbBedrooms());
              nbBathroom.setText(" " + listApp.get(listShell.getSelectionIndex()).getNbBathrooms());
              wifi.setText(" " + listApp.get(listShell.getSelectionIndex()).getWifi());
              tv.setText(" " + listApp.get(listShell.getSelectionIndex()).getTele());
              terrace.setText(" " + listApp.get(listShell.getSelectionIndex()).getTerrace());
            }
          }
        };
    listShell.addSelectionListener(selectApp);
  }
}
