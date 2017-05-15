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

app.controller('MicroserviceWizardModalCtrl', ['$scope', '$modalInstance', '$http','MicroServiceWizardFactory' ,
  function ($scope, $modalInstance, $http,MicroServiceWizardFactory) {
    $scope.modalData = {
    		microservice_name : "",
    		notes: "",
    		description : ""
        };

	
	$scope.init=function(){
		$scope.modalData=MicroServiceWizardFactory.activityToUpdate;
		$scope.microservice_name=$scope.modalData.microservice_name;
	}

    $scope.save = function () {
    	$scope.errorMsg=""
    	if($scope.modalData.microservice_name==null || $scope.modalData.microservice_name==""){
    		$scope.errorMsg="Wizard Name is Required";
    		return ;
    	}

      $modalInstance.close($scope.modalData);
      MicroServiceWizardFactory.setactivityToUpdate("");
      };
    
    $scope.update = function () {
    	$scope.errorMsg=""
    	if($scope.modalData.microservice_name==null || $scope.modalData.microservice_name==""){
    		$scope.errorMsg="Wizard Name is Required";
    		return ;
    	}
      $modalInstance.close($scope.modalData);
      MicroServiceWizardFactory.setactivityToUpdate("");
      $scope.modalData="";
    };
    
    $scope.close = function () {
    	MicroServiceWizardFactory.setactivityToUpdate("");
        $scope.modalData="";
        $modalInstance.dismiss('cancel');
    };
    
    $scope.init();
  }]);