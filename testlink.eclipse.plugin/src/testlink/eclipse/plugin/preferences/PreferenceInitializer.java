package testlink.eclipse.plugin.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import testlink.eclipse.plugin.Activator;


/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_REPORT_RESULTS_AFTER_TEST, true);
		store.setDefault(PreferenceConstants.P_TESTLINK_URL, "http://localhost/testlink_sandbox");
		store.setDefault(PreferenceConstants.P_DEV_KEY, "The TestLink database user table script_key field value goes here");
		store.setDefault(PreferenceConstants.P_TEST_CASE_CREATION_USER, "admin");
	}

}
