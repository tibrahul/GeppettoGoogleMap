
app.controller('ListModalCtrl', ['$scope', '$modalInstance', 'data', 'authFactory', '$http', 'RestURL', '$timeout', '$window', '$log',
  function ($scope, $modalInstance, data, authFactory, $http, RestURL, $timeout, $window, $log) {
	console.log("data " + angular.toJson(data));
	
	 $scope.modalData = {};
	 $scope.modalData.nounlist = data.nounlist;
	 $scope.modalData.stdlist = data.stdlist;
	 console.log("IN MODAL"+angular.toJson($scope.modalData.stdlist));
	 $scope.modalData.noun={selectedId:""};
	 $scope.modalData.std={selectedId:""};
	 $scope.errormessage = "";
	 $scope.ok = function () {
		 //alert( $scope.modalData.noun.selectedId)
		 if($scope.modalData.noun.selectedId && $scope.modalData.std.selectedId){
			 $scope.errormessage = "Please Select Only One Type!";
		 }else{
	      $modalInstance.close($scope.modalData);
		 }
	    };

	    $scope.close = function () {
	    	 $timeout(function() {
	    		 $modalInstance.dismiss('cancel');
	    	    }, 500);
	    	
	    };

  }]);