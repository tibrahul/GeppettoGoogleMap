/**
 * Created: July 7, 2015
 * @author Henrikh Kantuni
 * Note: controller for Angular UI Bootstrap Modals
 */

/**
 * Modified: July 13, 2015
 * @author Rashmi
 * Note: adding new project
 */

app.controller('MicroServiceEditModalCtrl', ['$scope', '$modalInstance','modalDataObj' ,'$http', 'MicroServiceWizardFactory', 
  function ($scope, $modalInstance, modalDataObj ,$http , MicroServiceWizardFactory) {
	
	$scope.modalData={};
	$scope.modalData=modalDataObj;
    $scope.modalData.gpMicroService = MicroServiceWizardFactory.gpMicroService;

    $scope.Update = function(gpMicroService){
    	$scope.modalData.UpdateCallBack(gpMicroService);
    	$modalInstance.close($scope.modalData);
    }
    
    $scope.Delete = function (gpMicroService) {
    	$scope.modalData.DeleteCallBack(gpMicroService);
    	$modalInstance.close($scope.modalData);
    };
    
    $scope.hideRemoveButton=false;
    $scope.hidebuttons=true;
    $scope.hideRemove=function(gpMicroService){
    	if(gpMicroService !=null){
    		$scope.hidebuttons=false;
    		if(gpMicroService.activities_json == null || gpMicroService.activities_json == ""){
        		$scope.hideRemoveButton=true;
        	}else{
        		$scope.hideRemoveButton=false;
        	}

    	}else{
    		$scope.hidebuttons=true;
    	}
    	
    	    }
    
    $scope.close = function () {
      $modalInstance.dismiss('cancel');
    };

  }]);