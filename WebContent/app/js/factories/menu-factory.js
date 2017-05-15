/**
 * @author Rashmi
 * created date: 3 August/15
 * 
 */
app.factory('MenuInformation', function() {
	return {
		projectId: 0,
		setProjectId: function(id) {
			this.projectId = id;
		},
		projectName: 0,
		setProjectName: function(name) {
			this.projectName = name;
		}
	};
});