/**
 * Created: Sep 17, 2015
 * @author Rashmi
 * Note: controller for Angular UI Bootstrap Modals
 */

app.controller('FBLoginModalCtrl', ['$scope', '$modalInstance', 'data','authFactory','$http','RestURL','$timeout', function ($scope, $modalInstance, data, authFactory,$http,RestURL,$timeout) {

    $scope.modalData = {};
    console.log("data " + angular.toJson(data
    		));

    $scope.modalData.name = "";
    $scope.modalData.type = "";
    $scope.modalData.email = "";

    $scope.modalData.multiselectsettings = {
        scrollableHeight: '200px',
        scrollable: true
    };

    //send when save message modal gets open
    $scope.modalData.message = data;
    
    //send when delete modal gets open
    $scope.modalData.id = data;
    
    $scope.ok = function () {
        $modalInstance.close($scope.modalData);
    };

    $scope.close = function () {
        $modalInstance.dismiss('cancel');
    };
    
   
		
}]);