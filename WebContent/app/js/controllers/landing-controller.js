var selectedProjectID = null;
var selectedProjectInfo = null;

app.controller('landingController', ['$scope', '$location', '$http', '$window', '$routeParams', '$modal',
    function ($scope, $location, $http, $window, $routeParams, $modal) {
        if ($routeParams.action == 'update') {
            $scope.templateUrl = 'app/views/en-US/edit-projects.html';
        } else if ($routeParams.action == 'list') {
            $scope.templateUrl = 'app/views/en-US/list-projects.html';
        } else if ($routeParams.action == 'advance') {
            $scope.templateUrl = 'app/views/en-US/advance-projects.html';
        }
        /*else{
         $scope.templateUrl = 'app/views/en-US/login.html';
         }*/

//    	alert("after" + $scope.templateUrl+$routeParams.action);

        $scope.open = function () {
            $modal.open({
                animation: true,
                templateUrl: 'app/views/en-US/templates/modals/project/create.html',
                controller: 'ModalCtrl',
                resolve: function () {
                    console.log('modal');
                }
            });
        };

    }]);