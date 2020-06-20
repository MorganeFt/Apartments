package io.github.oliviercailloux.y2018.apartments.gui;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.apartment.ApartmentFactory;
import io.github.oliviercailloux.y2018.apartments.valuefunction.ApartmentValueFunction;
import io.github.oliviercailloux.y2018.apartments.valuefunction.LinearAVF;
import io.github.oliviercailloux.y2018.apartments.valuefunction.LinearValueFunction;
import java.io.IOException;
import org.eclipse.swt.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;

/** @author AlWAZZAN & SAKHO */
/**
 * this class displays a list of apartments sorted according to the user's
 * utilities
 */
public class LayoutApartmentGUI {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreateApartmentGUI.class);

	java.util.List<Apartment> listApp;
	LinearAVF linearAVF;

	Display display = new Display();
	Shell shell = new Shell(display);

	/** add objects of apartments in a listShell */
	final List listShell = new List(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);

//  public LayoutApartmentGUI LayoutApartmentGUI() {
//    return new LayoutApartmentGUI();
//  }

	public LayoutApartmentGUI(LinearAVF newLinearAVF) {
		this.linearAVF = newLinearAVF;
		this.listApp = getListSorted(linearAVF);
	}

	/**
	 * General method which displays all the sorted apartment
	 *
	 * @param listApp the list of apartments to display
	 * @throws DOMException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	public void displayAppart() {
		LOGGER.info(listApp.size()+"");
		addAppinListShell();

		shell.setText("Sélection d'un appartement");

		// create a gridLayout of 3 columns
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		shell.setLayout(gridLayout);

		// create a griddata for a list of objects
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridData.horizontalSpan = 2;

		// create a list label
		Label label = new Label(shell, SWT.NULL);
		label.setText("Liste des appartements disponibles :");
		label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		new Label(shell, SWT.NULL);

		// define griddata with a verticalspan : 3 rows
		new Label(shell, SWT.NULL);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.verticalSpan = 3;

		gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL);
		gridData.verticalSpan = 4;
		gridData.heightHint = 400;
		listShell.setLayoutData(gridData);

		Group appartInfo = new Group(shell, SWT.NULL);
		appartInfo.setText("Détail sur l'appartement sélectionné :");

		gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		appartInfo.setLayout(gridLayout);

		gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridData.horizontalSpan = 2;
		appartInfo.setLayoutData(gridData);

		// beginning of creation of elements to display when we click on an apartement
		Label adresse;
		Label surface;
		Label prix;
		Label nbrChambres;

		new Label(appartInfo, SWT.NULL).setText("Adresse :");
		adresse = new Label(appartInfo, SWT.SINGLE | SWT.BORDER);
		adresse.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(appartInfo, SWT.NULL).setText("Surface :");
		surface = new Label(appartInfo, SWT.SINGLE | SWT.BORDER);
		surface.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(appartInfo, SWT.NULL).setText("Prix :");
		prix = new Label(appartInfo, SWT.SINGLE | SWT.BORDER);
		prix.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(appartInfo, SWT.NULL).setText("Nombre de Chambres :");
		nbrChambres = new Label(appartInfo, SWT.SINGLE | SWT.BORDER);
		nbrChambres.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		gridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
		gridData.horizontalSpan = 3;

		onClick(adresse, surface, prix, nbrChambres);

		shell.setSize(1000, 550);
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
	 * Method that creates a list of random apartments and tries them according to
	 * the utility of the user
	 *
	 * @param avf a way to rate the apartments
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	private static java.util.List<Apartment> getListSorted(LinearAVF avf) {

		java.util.List<Apartment> appart = ApartmentFactory.generateRandomApartments(10);

		appart.sort((Apartment c, Apartment d) -> {
			return -Double.compare(avf.getSubjectiveValue(c), avf.getSubjectiveValue(d));
		});

		return appart;
	}

	/**
	 * Method that adds available apartments in the shell list to display
	 *
	 * @param listApp2 the list of apartments to display
	 */
	public void addAppinListShell() {
		LOGGER.info(listApp.size()+"");
		for (Apartment a : listApp) {
			LOGGER.debug("Appart : " + a);
			listShell.add("Titre: " + a.getTitle() + "\t" + " Adresse : " + a.getAddress());
		}
		LOGGER.info("All apartment displayed");
	}

	/**
	 * Method that defines the action to execute when clicking on an apartment, here
	 * we display some elements of the apartment
	 *
	 * @param listApp3 the list of apartments to display
	 * @param adresse, surface, prix, nbrChambres the parameters of apps to display
	 *                 when clicking on an apartment
	 */
	private void onClick(Label adresse, Label surface, Label prix, Label nbrChambres) {
		// the listener when we click on an apartment
		SelectionAdapter selectApp = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				int[] selectedItems = listShell.getSelectionIndices();

				for (int loopIndex = 0; loopIndex < selectedItems.length; loopIndex++) {
					adresse.setText(listApp.get(listShell.getSelectionIndex()).getAddress());
					surface.setText(" " + listApp.get(listShell.getSelectionIndex()).getFloorArea());
					prix.setText(" " + listApp.get(listShell.getSelectionIndex()).getPricePerNight());
					nbrChambres.setText(" " + listApp.get(listShell.getSelectionIndex()).getNbBedrooms());
				}
			}
		};
		listShell.addSelectionListener(selectApp);
	}

	/**
	 * This is the main function
	 *
	 * @param args
	 * @throws IllegalAccessException
	 * @throws DOMException
	 * @throws IOException
	 */
//  public static void main(String[] args) throws IllegalAccessException, IOException {
//    ApartmentValueFunction avf = new ApartmentValueFunction();
//    avf.setFloorAreaValueFunction(new LinearValueFunction(0, 300));
//
//    LayoutApartmentGUI layout = new LayoutApartmentGUI(linearAVF);
//    layout.displayAppart();
//  }
}
