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

app.controller('WizardModalCtrl', ['$scope', '$modalInstance', '$http', 
  function ($scope, $modalInstance, $http) {

    $scope.modalData = {};

    $scope.modalData.name = "";
    $scope.modalData.description = "";

    $scope.ok = function () {
      $modalInstance.close($scope.modalData);
    };

    $scope.close = function () {
      $modalInstance.dismiss('cancel');
    };

  }]);