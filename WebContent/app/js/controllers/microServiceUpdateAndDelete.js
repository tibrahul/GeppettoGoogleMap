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

app.controller('MicroServiceUpdateDelete', ['$scope','$modalInstance','modalDataObj','$http', 'MicroServiceWizardFactory', 
  function ($scope,$modalInstance,modalDataObj,$http , MicroServiceWizardFactory) {
	
	
	$scope.modalData=modalDataObj;

    $scope.Update = function(){
    	$scope.modalData.UpdateCallBack($scope.modalData.wizard);
    	$modalInstance.close($scope.modalData.wizard);
    }
    
    $scope.Delete = function () {
    	$scope.modalData.DeleteCallBack($scope.modalData.wizard);
    	$modalInstance.close($scope.modalData);
    };
    
	$scope.close=function(){
		  $modalInstance.dismiss('cancel');	
	};	


  }]);