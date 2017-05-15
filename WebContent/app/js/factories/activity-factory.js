// Date Created :  April 08 2015
// @author Vijay
// Purpose of this controller to update the existing noun and nounattributes.
// since .75

app.factory('ActivityInformation', function() {
	return {
		activity : {},
		setActivtyInformantion : function(activityinfo) {
			this.activity = activityinfo;
		},
		deviceTypes : {},
		setDeviceTypes : function(device){
			this.deviceTypes=device;
		},
		projectId: 0,
		setProjectId: function(id) {
			this.projectId = id;
		},
		activityId: 0,
		setActivityId: function(id) {
			this.activityId = id;
		},
		activityName: 0,
		  setActivityName: function(name) {
		   this.activityName = name;
		  },
		noun: {},
		setNoun: function(noun) {
			this.noun = noun;
		},		
		checkboxgroupArray : {
			checkboxgroup : []
		},
		setCheckboxgroupArray: function(array) {
			this.checkboxgroupArray.checkboxgroup = array;
		},
		radioArray : {
			radiogroup : []
		},
		setRadioArray: function(array) {
			this.radioArray.radiogroup = array;
		} 
				
	};
});