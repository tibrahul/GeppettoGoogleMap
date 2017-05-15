app.controller('OrganizationCtrl', ['$log', '$scope', '$http', '$timeout', '$location',
  '$cookieStore', 'RestURL','$routeParams','$modal','UserDetailService','$modal',
  function ($log, $scope, $http, $timeout, $location, $cookieStore, RestURL, $routeParams,$modal,UserDetailService,$modal) {

    var self = $scope;
    
    console.log("MY Organoization Controlller -- ")   
    
    self.individual_user = [];
    self.selectedUsers =[];
        
        $http.get(RestURL.baseURL + 'updateuser/get_all_users/').success(function (response) {
            self.users = response;
            for (var i = 0; self.users [i]; ++i) {
                var individual = {
                  id: self.users[i]['id'],
                  label: self.users[i]['username']
                };
                $scope.individual_user.push(individual);
              }
          }).error(function (response) {
            $log.log('Error : ', response);
          });
        
        
        $scope.openSaveDialog = function (size, msg) {
            var modalInstance = $modal.open({
              animation: true,
              size: size,
              templateUrl: 'app/views/en-US/templates/modals/project/savemessage.html',
              controller: 'ModalCtrl',
              resolve: {
                data: function () {
                  return msg;
                }
              }
            });
            modalInstance.result.then(function (dataFromModal) {                
            }, function () {
              $log.info('Modal dismissed at: ' + new Date());
              $location.url("/en-US/projects");
            });
          };
          
        self.createOrganization = function(){
        	self.organization.members_in_organization = self.selectedUsers;
        	$http.post(RestURL.baseURL + 'organization/create_organization/', self.organization)
            .success(function (data) {
           	 UserDetailService.organizationForLoggedUserDetail(data);
           	console.log(angular.toJson(UserDetailService.organizationForLoggedUser));
           	 $log.log("Organization details for Signed in USER : ", JSON.stringify(data));
           	 
           	$scope.openSaveDialog('sm', "Organization saved successfully");
           	

        	  }).error(function () {
          //callback 
          //$location.url("/en-US/login");
        });
        }
}]);