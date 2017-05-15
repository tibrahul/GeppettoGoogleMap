/**
 * 
 */

app.controller('InstallrCtrl', ['Facebook', '$modal', '$log', '$scope', '$http', '$location', '$cookieStore', 'RestURL', 'authFactory','$rootScope','$routeParams',
  function (Facebook, $modal, $log, $scope, $http, $location, $cookieStore, RestURL, authFactory,$rootScope,$routeParams) {

	$log.log('Coming inside the InstallrCtrl');
	
	$scope.installrDetail = {};
	
	$scope.saveInstllrDtlsAndStatusChng = function(installrData){
		$log.log('installrData data =>',angular.toJson(installrData));
		$scope.installrDetail.id  = $routeParams.newuserId;
		$log.log('UserDetailService.getLoggedUserId',$scope.installrDetail.id);
		$log.log('Ready to feed data of Installr table',angular.toJson($scope.installrDetail));
//		if($scope.installrDetail.$valid){
			$http({
	            url: RestURL.baseURL + 'login/store_the_Installr_details',
	            method: 'POST',
	            data: angular.toJson($scope.installrDetail),
	            /*params: {user_id: user.id},*/
	            //withCredentials: true,
	            header: {
	                "Accept": "application/json"
	            }
	        }).success(function(response) {
	        	console.log('Data Saved Successfully in to database')
	        	if(response != null){
	        		if(response === true){
	        			console.log('Coming from true')
	        			// Need to change the Status OPEN TO COMPLETED 
	        			$location.url("/en-US/new_user")
	        		}else{
	        			console.log('Not add somthing went wrong or already data  need to override')
	        		}
	        	}
	        }).error(function(response){
	        	alert('When Save Installr data Into db something went wrong')
	        });
//		}
//		console.log('Coming insdie the method saveInstllrDtlsAndStatusChng',angular.toJson(installrData));
	}
	
}]);