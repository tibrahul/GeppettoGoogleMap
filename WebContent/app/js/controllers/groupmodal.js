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

app.controller('GroupModalCtrl', ['$scope', '$modalInstance', '$http','ActivityInformation','data',
  function ($scope, $modalInstance, $http,ActivityInfo,data) {
    $scope.modalData = {
        };

	
	$scope.init=function(){
		
		 $scope.modalData.primary_Noun_name = ActivityInfo.activity.primary_noun.name;
		 $scope.modalData.primary_Noun = ActivityInfo.activity.primary_noun;
		 $scope.modalData.nounattributes = ActivityInfo.activity.primary_noun.nounattributes;
		
	      if(data.associateNounData){
	    	  $scope.modalData.pn_attribute = data.associateNounData.pn_attribute;
	      }
	}

	$scope.choosePnAttribute = function(){
		for (var i = 0; i < $scope.modalData.nounattributes.length; i++) {
	      if ($scope.modalData.nounattributes[i]['id'] == $scope.modalData.pn_attribute_id) {
	    	  $scope.modalData.pn_attribute = $scope.modalData.nounattributes[i];
	      }
	    }
	}
	
    $scope.save = function () {
    	$scope.errorMsg=""
    	if($scope.modalData.name==null || $scope.modalData.name==""){
    		$scope.errorMsg="Group Name is Required";
    		return ;
    	}

      $modalInstance.close($scope.modalData);
      };
    
    $scope.close = function () {
        $scope.modalData="";
        $modalInstance.dismiss('cancel');
    };
    
    $scope.init();
  }]);