package io.github.oliviercailloux.y2018.apartments.gui;

import com.google.common.base.Preconditions;
import io.github.oliviercailloux.y2018.apartments.iconDisplay.DisplayIcon;
import io.github.oliviercailloux.y2018.apartments.valuefunction.ApartmentValueFunction;
import io.github.oliviercailloux.y2018.apartments.valuefunction.Criterion;
import io.github.oliviercailloux.y2018.apartments.valuefunction.profile.Profile;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class is a class that will allow us to demonstrate. We ask a few questions to the user at
 * the beginning, we adapt his utility then we show him a list of apartments according to his
 * preferences
 */
public class ProfileGUI {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProfileGUI.class);
  private static final int marginHorizontal = 20;
  private static final int marginVertical = 30;
  private static final int marginImages = 10;
  private static final int sizeImages = 100;


  private static final List<ProfileType> profileTypesAvailable = List.of(ProfileType.getAvailableTypes());

  Display display;
  Shell shell;

  public ProfileGUI() {
    this.display = new Display();
    this.shell = new Shell(display);
    shell.setText("Profile selection - Questions");
    shell.setLayout(new GridLayout());
    shell.setSize(2*marginHorizontal+(profileTypesAvailable.size()-1)*marginImages+profileTypesAvailable.size()*sizeImages,
            2*marginVertical+sizeImages);
    int x = (display.getClientArea().width - shell.getSize().x) / 2;
    int y = (display.getClientArea().height - shell.getSize().y) / 2;
    shell.setLocation(x,y);

  }

  /**
   * This is the main function, it asks Questions , AdaptAnswers and then displays the list of
   * Apartments
   *
   * @param args
   */
  public static void main(String[] args) throws IOException {
    ProfileGUI profileSelector = new ProfileGUI();
    profileSelector.askForProfile();

    LOGGER.info("Begining the Layout.");

     /*ProfileGUI lay = new ProfileGUI(avf);
     lay.displayAppart();*/
  }

  /**
   * This function will create and display the window (interface) to ask the user questions and to
   * determine later the weight of his choices
   */
  public void askForProfile() throws IOException {
    Profile selected;
    Map<ProfileType, Image> logos = new HashMap<>();
    Map<ProfileType,Button> buttons = new ArrayList<>();
    for(int i = 0; i<profiles.size()){
      try (InputStream f = DisplayIcon.class.getResourceAsStream(profileTypesAvailable.get(i)+".png")) {
        Image image = new Image(display, f);
        Button b = new Button(shell,SWT.PUSH);
        int x = marginHorizontal+i*(sizeImages+marginImages);
        int y = marginVertical;
        b.setBounds(x,y,sizeImages,sizeImages);
        b.setImage(image);
        Listener selectionlistener =
                new Listener() {
                  @Override
                  public void handleEvent(Event event) {

                    selected =ProfileManager.getProfile(profileTypesAvailable.get(i));
                    shell.close();
                  }
                };
        buttons.put(profiles.get(i),b);
        logos.put(profiles.get(i), image);
      }

    }
    shell.setBackground(new Color(display, new RGB(180,180,180),0));
    shell.pack();
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    shell.close();
    display.dispose();









    // the listener when we click on finish
    /*Listener finishlistener =
        new Listener() {
        @Override
		public void handleEvent(Event event) {

            Preconditions.checkArgument(
                !text1.getText().equals(""), "il faut saisir un chiffre :(");
            Preconditions.checkArgument(!text2.getText().equals(""), "il faut saisir un texte :(");

            surfaceMin = Double.parseDouble(text1.getText());
            nbBedMin = Double.parseDouble(text2.getText());

            shell.close();

            LOGGER.info("Les attributs importants sont : ");
            for (int j = 0; j < moreImportantAttributes.size(); j++) {
              LOGGER.info(moreImportantAttributes.get(j));
            }

            LOGGER.info("Les attribut moins importants sont : ");
            for (int j = 0; j < lessImportantAttributes.size(); j++) {
              LOGGER.info(lessImportantAttributes.get(j));
            }
            LOGGER.info(
                "la valeur minmum de la surface est "
                    + surfaceMin
                    + "\nLa valeur minimum du nbre de chambre est "
                    + nbBedMin);
          }
        };
*/
    // This is a submit button, it will close the shell when the user click on
    // Terminer
    final Button finish = new Button(shell, SWT.PUSH);
    finish.setText("Terminé");
    finish.pack();
    finish.addListener(SWT.Selection, finishlistener);


    // open the window
    shell.open();
    LOGGER.info("The Shell was opened with success.");
    while (!shell.isDisposed()) {

      if (!display.readAndDispatch()) {

        display.sleep();
      }
    }
    display.dispose();
    LOGGER.info("The screen was closed with success.");
  }

  /**
   * Method that responds when we click on a button. It needs to retrieve the two buttons that
   * correspond to the items to choose
   *
   * @param pressedButton the choice of the user
   * @param unPressedButton the other choice not selected
   */
  public void clickOnButton(Button pressedButton, Button unPressedButton) {
    Label boo = new Label(shell, SWT.NULL);
    pressedButton.addSelectionListener(
        new SelectionAdapter() {
        @Override
		public void widgetSelected(SelectionEvent e) {
            Button source1 = (Button) e.widget;

            if (source1.getSelection() && !moreImportantAttributes.contains(source1.getText())) {
              moreImportantAttributes.add(source1.getText());
              lessImportantAttributes.add(unPressedButton.getText());
            }

            if (pointer == choix1.size() || pointer == choix2.size()) {

              pressedButton.setEnabled(false);
              unPressedButton.setEnabled(false);

              boo.setText(
                  "\"Merci pour vos choix, nous allons chercher pour vous les meilleurs"
                      + " apartements :) ");
              boo.pack();
            } else {

              pressedButton.setText(choix1.get(pointer).toString());
              unPressedButton.setText(choix2.get(pointer).toString());
              pointer++;
              /**
               * this will change the text of the buttons by choosing the content of the arrays
               * attributImportant and attributPasImportant
               */
            }

            /** in order not to be selected by default in the question in the next iteration */
            pressedButton.setSelection(false);

            pressedButton.pack();
            unPressedButton.pack();
          }
        });
  }

  /** This function will adapt the utility of the user using ApartmentValueFunction */
  public ApartmentValueFunction adaptAnswers(ApartmentValueFunction avf) {

    // we collect the answers on the minimums and we adapt the utility of the user
    avf.adaptBounds(Criterion.NB_BEDROOMS, nbBedMin, true);
    avf.adaptBounds(Criterion.FLOOR_AREA, surfaceMin, true);

    // we collect the answer of the first Question and adapt the utility of the user
    if (moreImportantAttributes.get(0).equals("WIFI")
        && lessImportantAttributes.get(0).equals("TERRACE")) {
      avf.adaptWeight(Criterion.WIFI, Criterion.TERRACE);
    } else {
      avf.adaptWeight(Criterion.TERRACE, Criterion.WIFI);
    }

    // we collect the answer of the second Question and we adapt the utility of the
    // user
    if (moreImportantAttributes.get(1).equals("TELE")
        && lessImportantAttributes.get(1).equals("PRICE_PER_NIGHT low")) {
      avf.adaptWeight(Criterion.TELE, Criterion.PRICE_PER_NIGHT);
    } else {
      avf.adaptWeight(Criterion.PRICE_PER_NIGHT, Criterion.TELE);
    }

    return avf;
  }

  private void centerDisplay(Display d, Shell s){
    int x = (display.getClientArea().width - shell.getSize().x) / 2;
    int y = (display.getClientArea().height - shell.getSize().y) / 2;
    shell.setLocation(x,y);
  }
}
