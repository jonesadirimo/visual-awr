package org.altaprise.vawr.ui.bulkfileschartwizard;

import java.util.ArrayList;

import org.altaprise.vawr.ui.common.WizardContentBasePanel;
import org.altaprise.vawr.ui.common.WizardRootPanel;

public class BulkFileWizardRootPanel extends WizardRootPanel {
    public BulkFileWizardRootPanel() {

        super();

        ArrayList<WizardContentBasePanel> wizardPanels = new ArrayList<WizardContentBasePanel>();
        //wizardPanels.add(new DBConnectPanel());
        //wizardPanels.add(new SelectDBIdPanel());
        //wizardPanels.add(new SnapIdSelectPanel());
        //wizardPanels.add(new AWRQueryPanel());
        //wizardPanels.add(new ChartPanel());

        setWizardPanels(wizardPanels);
    }

    protected void setWizardPanels(ArrayList<WizardContentBasePanel> wizardPanels) {
        WIZARD_PANEL_DECK = wizardPanels;
        try {
            super.jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
