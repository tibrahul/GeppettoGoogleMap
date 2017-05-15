/**
 * @author Rashmi
 * created date: 3 August/15
 * 
 */
app.factory('MicroServiceWizardFactory', function() {
	return {
		gpMicroService : {},
		setgpMicroService : function(gpMicroService) {
			this.gpMicroService = gpMicroService;
		},modalData : {},
		setModalData : function(modalData) {
			this.modalData = modalData;
		},WizardData : {},
		setWizardData : function(WizardData) {
			this.WizardData = WizardData;
		},activityToUpdate : {},
		setactivityToUpdate : function(activityToUpdate){
			this.activityToUpdate=activityToUpdate;
		}
	}});