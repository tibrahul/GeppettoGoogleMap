/**
 * Modified: July 13, 2015
 * @author Rashmi
 * Note: integrating new screen design's for login and project , integrating new add project and delete project popup
 */

app.controller('HomeCtrl', ['$log', '$scope', '$http', '$location', '$cookieStore', 'RestURL', 'authFactory', '$cookies',
  function ($log, $scope, $http, $location, $cookieStore, RestURL, authFactory, $cookies) {

    var self = $scope;
    //logout
    self.logout = function () {
      $log.log('Remove All cookies');
      angular.forEach($cookies, function (v, k) {
        $cookieStore.remove(k);
      });

      //call rest URL to remove session data on server
      $http.post(RestURL.baseURL + 'j_spring_security_logout')
        .success(function (data) {
          $log.log("logout success" + data);
        }).error(function (data) {
        $log.log("logout failure" + data);
      });
    };

    self.logout();

  }]);