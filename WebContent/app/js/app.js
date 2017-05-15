var app = angular.module('Geppetto', ['facebook', 'ngRoute', 'ngCookies', 'ngMessages', 'ui.bootstrap',
  'ui.grid', 'ui.grid.edit','ui.grid.resizeColumns', 'ui.tree', 'imageupload', 'colorpicker.module','ui.grid.cellNav','ui.grid.selection']);

app.config(['$routeProvider', 'FacebookProvider', function ($routeProvider, FacebookProvider) {
  FacebookProvider.init('1619632164961533');

  $routeProvider.when('/', {
    redirectTo: '/en-US/home/'
  }).when('/en-US/home/', {
    templateUrl: 'app/views/en-US/home.html',
    controller: 'HomeCtrl'
  }).when('/en-US/login/', {
    templateUrl: 'app/views/en-US/login.html',
    controller: 'LoginCtrl'
  }).when('/en-US/projects/', {
    templateUrl: 'app/views/en-US/user/projects.html',
    controller: 'ProjectCtrl'
  })
  .when('/en-US/mac_config/', {
	  templateUrl: 'app/views/en-US/ipastuff/mac_config.html',
	  controller: 'MacCtrl'
	   }).when('/en-US/installr_user/:newuserId', {
		   templateUrl: 'app/views/en-US/ipastuff/installr_user_config.html',
		   controller: 'InstallrCtrl'
		    })
		    .when('/en-US/new_user/', {
 templateUrl: 'app/views/en-US/user/new_user.html',
 controller: 'NewUserCtrl'
  }).when('/en-US/organization/', {
	  templateUrl: 'app/views/en-US/user/organization.html',
	  controller: 'OrganizationCtrl'
	   }).when('/en-US/project/:action/', {
    templateUrl: 'app/views/en-US/user/project.html',
    controller: 'ProjectCtrl'
  }).when('/en-US/activity/:action/', {
    templateUrl: 'app/views/en-US/user/activity.html'
  }).when('/en-US/noun/:action/', {
    templateUrl: 'app/views/en-US/user/noun.html'
  }).when('/en-US/axe/desktop/:action/', {
    templateUrl: 'app/views/en-US/user/desktop.html'
  }).when('/en-US/axe/tablet/:action/', {
    templateUrl: 'app/views/en-US/user/tablet.html'
  }).when('/en-US/axe/mobile/:action/', {
    templateUrl: 'app/views/en-US/user/mobile.html'
  }).when('/en-US/about/', {
    templateUrl: 'app/views/en-US/about.html'
  }).when('/en-US/services/', {
    templateUrl: 'app/views/en-US/services.html'
  }).when('/en-US/contact/', {
    templateUrl: 'app/views/en-US/contact.html'
  }).when('/en-US/404/', {
    templateUrl: 'app/views/en-US/404.html'
  }).when('/en-US/projecttemplate/:action/', {
    templateUrl: 'app/views/en-US/user/project-template.html',
    controller: 'ProjectTemplateCtrl'
  }).otherwise({
    redirectTo: '/en-US/404/'
  });
}]);

app.run(['$log', '$rootScope', '$modal', '$location', function ($log, $rootScope, $modal, $location) {
  $log.log('loading application!');

  // Save the User Logged In ROle Info 
  $rootScope.roleOfLoggedUser = "";
  
  // Save the Logged In User Info
  $rootScope.loggedInUserData  ="";
  /**
   * 
   *  Sign out only clear the user related rootscope object nt clearing here cookies and sessions
   */
  $rootScope.signOut = function(){
  	console.log('Coming inside the Sign Out method');
  	$rootScope.roleOfLoggedUser = "";
  	$location.url("/en-US/home/");
  }
  
  
  $rootScope.browserWarningDialog = function (size, msg) {
    var modalInstance = $modal.open({
      animation: true,
      keyboard: true,
      size: size,
      templateUrl: 'app/views/en-US/templates/modals/project/browser_warning.html',
      controller: 'ModalCtrl',
      resolve: {
        data: function () {
          return msg; // deep copy
        }
      }
    });

    modalInstance.result.then(function (dataFromModal) {

    }, function () {
      $log.info('Modal dismissed at: ' + new Date());
    });
  };

  var isOpera = !!window.opera || navigator.userAgent.indexOf(' OPR/') >= 0;
  // Opera 8.0+ (UA detection to detect Blink/v8-powered Opera)
  var isFirefox = typeof InstallTrigger !== 'undefined';   // Firefox 1.0+
  var isSafari = Object.prototype.toString.call(window.HTMLElement).indexOf('Constructor') > 0;
  // At least Safari 3+: "[object HTMLElementConstructor]"
  var isChrome = !!window.chrome && !isOpera;              // Chrome 1+
  var isIE = /*@cc_on!@*/false || !!document.documentMode;   // At least IE6
  var isChrome = !!window.chrome && !isOpera;              // Chrome 1+
  if (!isChrome) {
    var msg = "Not all features may work correctly with this browser! Please use chrome!";
    $rootScope.browserWarningDialog('sm', msg);
  }
}]);