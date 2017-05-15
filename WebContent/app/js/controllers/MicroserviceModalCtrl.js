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

app.controller('MicroServiceModalCtrl', ['$scope', '$modalInstance', '$http', 'MicroServiceWizardFactory', 
  function ($scope, $modalInstance, $http , MicroServiceWizardFactory) {
	
	
	$scope.modalData={};
    $scope.modalData.gpMicroService = MicroServiceWizardFactory.gpMicroService;
    $scope.modalData.gpMicroService.selected=false;

    $scope.save = function(gpMicroService){
    	$scope.modalData=gpMicroService;
    	$modalInstance.close($scope.modalData);
    }
    
    $scope.close = function () {
      $modalInstance.dismiss('cancel');
    };

  }]);