/**
 * @author : SureshAnand
 * This UserDetail service can hold the data Of logged in User related Information  and
 * Roles related details data from database 
 */

app.service("UserDetailService", [ "$log", "$http", "$timeout", "$location", function($log, $http, $timeout, $location) {
	var self = this;
	self.loggedUserDetail = {};
	self.loggedUserType = "";
	self.loggedUserId = "";
	self.organizationForLoggedUser = {};

	$log.log("Loaded the UserDetailService");

	self.saveLoggedUserDetails = function(loggeduserdata) {
		$log.log('Inside saveLoggedUserDetails');

		self.loggedUserDetail = angular.copy(loggeduserdata);
		$log.log('Data of self.loggedUserDetail', angular.toJson(self.loggedUserDetail));

		// Added the Logged In User Type
		self.loggedUserType = angular.copy(self.loggedUserDetail.role);
		$log.log('loggedUserType user role', angular.toJson(self.loggedUserType));

		// Added the Logged In User Id
		self.loggedUserId = angular.copy(self.loggedUserDetail.userid);
		$log.log('loggedUserId user id', angular.toJson(self.loggedUserId));

	};
	
	self.organizationForLoggedUserDetail = function(organizationObject){
		self.organizationForLoggedUser = angular.copy(organizationObject);
	}

	self.getLoggedUserRole = function() {
		$log.log('Inside getLoggedUserRole');
		return self.loggedUserType;
	};

	self.getLoggedUserId = function() {
		$log.log('Inside getLoggedUserId');
		return self.loggedUserId;
	};

} ]);