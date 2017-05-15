/**
 * Modified: July 13, 2015
 * @author Rashmi
 * Note: integrating new screen design's for login and project , integrating new add project and delete project popup
 */
/**
 * Modified
 * @author Rashmi
 * Note: FB/G+ login with spring security
 */
/**
 * Modified
 * @author Rashmi
 * Note: FB email address modal functionality implemented
 */
app.controller('LoginCtrl', ['Facebook', '$modal', '$log', '$scope', '$http', '$location', '$cookieStore', 'RestURL', 'authFactory','$rootScope','UserDetailService',
  function (Facebook, $modal, $log, $scope, $http, $location, $cookieStore, RestURL, authFactory,$rootScope,UserDetailService) {

    var self = $scope;
    

    self.isNewUser = false;

    self.user = {};
    self.organization = {
    	    "contact_phone":"",
    	    "about":""
    }

    /* $('.carousel').carousel({
     interval: 6000,
     pause: 'hover',
     wrap: true
     }).on('click', '.carousel-control', handle_nav);

     var handle_nav = function (e) {
     e.preventDefault();
     var nav = $(this);
     nav.parents('.carousel').carousel(nav.data('slide'));
     };*/
    self.init = function () {
      $log.log('Initializing login controller!');

      $log.log('Remove All cookies');
      $cookieStore.remove('back');
      $cookieStore.remove('activity');

      self.user.newuser = false;
      /**
       * External scripts to load
       */
      $('.accordion').unbind();
      $('.accordion').click(function () {
        $('div.more-fields').toggleClass('active');
        $('div.more-fields').slideToggle();
      });
    };

    /**
     * login process
     */
    self.doLogin = function (loginform, user) {
      $log.log('do login is called!');
      $log.log('username:', self.user.email);
      $log.log('password:', self.user.pass);
      $log.log('newuser:', self.user.newuser);
      $log.log('organization --- > >>> :', self.organization.organization_name);
      self.user.username = self.user.email;
      self.user.password = self.user.pass;
      
      if (loginform.$valid && self.user.newuser==true) {
//        alert('Signup success!'+angular.toJson(self.user));
    	  console.warn("self.user- > ",self.user)
        $http.post(RestURL.baseURL + 'updateuser/register_user/', self.user)
        .success(function (data) {
        	$log.log("Valid SIGNUP: ", JSON.stringify(data));
        	self.organization.user_id = data.id;
        	// $http.post(RestURL.baseURL + 'organization/create_organization/', self.organization)
             //.success(function (data) {
            	// UserDetailService.organizationForLoggedUserDetail(data);
            	 //console.log(angular.toJson(UserDetailService.organizationForLoggedUser));
            	 //$log.log("Organization details for Signed in USER : ", JSON.stringify(data));
          $http.post(RestURL.baseURL + 'j_spring_security_check?j_username=' + self.user.email + '&j_password=' + self.user.pass)
          .success(function (data) {
            $log.log("Valid login: ", JSON.stringify(data));
            if (angular.equals(data.status,"failed")) {
              $scope.errormessage = " No Mac Found,Please contact Service Provider";
              loginform['email'].$dirty = true;
              loginform['password'].$dirty = true;
            } else {
            	
              var user = data;
              authFactory.setUser(user);
              $location.url("/en-US/projects");
            }
          //}).error(function () {
          //callback 
          //$location.url("/en-US/login");
        //});
          })
          .error(function () {
              //callback for organization insertion
              //$location.url("/en-US/login");
            });         /* var user = data;
            authFactory.setUser(user);
            $location.url("/en-US/projects");*/
        }).error(function (e) {
        	$log.log('error'+e);
            $scope.errormessage = "User Already Exists";
            loginform['email'].$dirty = true;
            loginform['password'].$dirty = true;
        });
      	} else if (loginform.$valid) {
        $http.post(RestURL.baseURL + 'j_spring_security_check?j_username=' + self.user.email + '&j_password=' + self.user.pass)
          .success(function (data) {
            $log.log("Valid login: ", JSON.stringify(data));
            if (data.status == "failed") {
              $scope.errormessage = " Your user name or password is wrong. Please try again!";
              loginform['email'].$dirty = true;
              loginform['password'].$dirty = true;
            } else {
              var user = data;
              if(data.role === "ROLE_ADMIN"){
                  console.log('only coming as a role admin');
                  $rootScope.roleOfLoggedUser = data.role;
                  // For stored the logged in User data In the below variable bcz we can reuse the 
                  // $rootScope var and use it.
                  $rootScope.loggedInUserData  = data;
                  UserDetailService.saveLoggedUserDetails(data);
                  console.warn(angular.toJson(data))//organizationForLoggedUserDetail
                  
                 }else{
                  console.log('Coming because he got null its null');
                 }
                 console.log('Logged in user :',angular.toJson($rootScope.roleOfLoggedUser));
              authFactory.setUser(user);
              console.log("user.userid-- > ",user.userid)
              $http.get(RestURL.baseURL + 'organization/getOrganization_by_user_id/'+user.userid)
              .success(function (data) {
            	  UserDetailService.organizationForLoggedUserDetail(data);
              }).error(function () {
                  //callback for get organization based on User id logged in  
            	  
                });
              
              $location.url("/en-US/projects");
            }
          }).error(function () {
          //callback 
          //$location.url("/en-US/login");
        });
      } else {
        $log.log('form is invalid!');
        if (self.isNewUser) {
          loginform['username'].$dirty = true;
          loginform['usrtel'].$dirty = true;
        }
        loginform['email'].$dirty = true;
        loginform['password'].$dirty = true;
      }
    };


    /**
     * Facebook Login implementation
     */

    $scope.fbLogin = function () {
      Facebook.login(function (response) {
        if (response.status == 'connected') {
          console.log("FB connected");
          $log.log("Valid login: ", JSON.stringify(response));
          $scope.me(response.authResponse.accessToken);
        } else {
          console.log("FB not connected");
        }
      }, {
        scope: 'email,user_website,user_photos',
        return_scopes: true
      });
    };

    /**
     * fetch FB user email and name
     */
    $scope.me = function (accessToken) {
      Facebook.api('/me?fields=email,name', function (authResponse) {
        $scope.fbuser = authResponse;
        console.debug("$scope.user.name" + angular.toJson($scope.fbuser));
        checkEmail($scope.fbuser.email, $scope.fbuser.name, 'fb');
      });
    };

    /**
     * G+ login implementation
     */
    $scope.gplusLogin = function (callback) {
      $scope.clientId = "24932703999-ve2oh92tsibhau5j0du6skunvi29g7og.apps.googleusercontent.com";
      gapi.auth.signIn({
        'callback': function (authResult) {
          $scope.signinCallback(authResult, callback);
        },
        'clientid': $scope.clientId,
        'cookiepolicy': 'single_host_origin',
        'data-accesstype': 'offline',
        'scope': 'https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/userinfo.email',
        'data-requestvisibleactions': 'http://schemas.google.com/AddActivity'
      });
    };

    $scope.signinCallback = function (authResult, callback) {

      if (authResult['status']['signed_in']) {
        console.log("SUCCESS");
        gapi.client.load('plus', 'v1', function () {
          var request = gapi.client.plus.people.get({'userId': 'me'});
          request.execute(function (response) {
            console.log('ID: ' + angular.toJson(response));
            console.log('Display Name: ' + response.displayName);
            var email = '';
            if (response['emails']) {
              for (i = 0; i < response['emails'].length; i++) {
                if (response['emails'][i]['type'] == 'account') {
                  email = response['emails'][i]['value'];
                }
              }
            }
            console.log('email : ' + email);
            //send response to server
            gotoServer(email, response.displayName, 'gplus');
          });
        });

      } else if (authResult['error']) {
        console.log("G+ NOT CONNECTED");
      }
    };

    /**
     * This method checks if email address of signed in FB user exists or not,
     * if not then it will ask for the same in a modal window.
     */
    $scope.modalData = {};
    $scope.fb = {};
    function checkEmail(email, name, type) {
      $scope.$apply(function () {
        if (email == null || email == undefined) {
          var tempdata = {"name": name, "type": type};
          var modalInstance = $modal.open({
            animation: true,
            templateUrl: 'app/views/en-US/templates/modals/project/get_email.html',
            controller: 'FBLoginModalCtrl',
            resolve: {
              data: function () {
                return angular.copy(tempdata); // deep copy
              }
            }
          });

          modalInstance.result.then(function (dataFromModal) {
            $scope.modalData = dataFromModal;
            //populate scope vars with modal data
            $scope.fb.email = $scope.modalData.email;
            $scope.fb.name = $scope.modalData.name;
            $scope.fb.type = $scope.modalData.type;
            gotoServer($scope.fb.email, $scope.fb.name, $scope.fb.type);

          }, function () {
            $log.info('Modal dismissed at: ' + new Date());
          });

        } else {
          gotoServer(email, name, type);
        }
      });

    }

    /**
     * after social sign in go to server for spring security and creation of new user if not exist
     */
    function gotoServer(email, name, type) {
      $http.post(RestURL.baseURL + 'j_spring_social_security_check?email=' + email + '&username=' + name + '&socialtype=' + type)
        .success(function (data) {
          $log.log("Valid login: ", JSON.stringify(data));
          if (data.status == "failed") {
            $scope.errormessage = " Your user name or password is wrong. Please try again!";
            loginform['email'].$dirty = true;
            loginform['password'].$dirty = true;
          } else {
            var user = data;
            authFactory.setUser(user);
            $location.url("/en-US/projects");
          }
        }).error(function () {
        console.log("ERROR WITH SOCIAL SIGNIN");
      });
    }

    self.init();

  }]);