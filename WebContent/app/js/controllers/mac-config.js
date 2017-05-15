/**
 * 
 */

app.controller('MacCtrl', ['Facebook', '$modal', '$log', '$scope', '$http', '$location', '$cookieStore', 'RestURL', 'authFactory','$rootScope',
  function (Facebook, $modal, $log, $scope, $http, $location, $cookieStore, RestURL, authFactory,$rootScope) {
	
	$log.log('Coming inside the MacCtrl')
	
	$scope.saveAndShwInstallrScrn = function(){
		console.log('coming In Mac Ctrl');
		// Need to save the Mac Config data Into database
		$location.url("/en-US/installr_user")
	}
	
}]);