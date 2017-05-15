/**
 * Modified:
 * @author Pravin
 * Note: integrating activity and noun
 */

/**
 * Modified: July 13, 2015
 * @author Rashmi
 * Note: integrating new screen design's for login and project , integrating new add project and delete project modal
 */

/**
 * Modified: July 30, 2015
 * @author Rashmi
 * Note: edit project not saving values bug fixed
 */

/**
 * Modified: May 31, 2016
 * @author Kumaresan Perumal
 * Note: We had a problem with noun creation that is createNounNow() method. I added some code in createNounNow method to 
 * give response for client to know the noun exist or not and i added a modal variable name 'instance'
 */

app.controller('ProjectCtrl', ['$scope','$rootScope', '$routeParams', '$log', '$location', '$http',
  '$timeout', '$cookieStore', '$window', 'ProjectData', 'languageFactory', 'RestURL',
  'ActivityInformation', 'authFactory', '$modal', 'MenuInformation',
  function ($scope,$rootScope,$routeParams, $log, $location, $http, $timeout, $cookieStore, $window,
            ProjectData, languageFactory, RestURL, ActivityInfo, authFactory, $modal, MenuInfo) {

    console.log("inside project controller")
	window.ProjectControllerScope = $scope;

    $rootScope.actobj1=[];
    var link = '';
    $scope.getProjectsForUser = function () {
      $http.get(RestURL.baseURL + 'project/get_projects_for_user/')
        .success(function (data) {
          $scope.json = data;
          $('.projects > ul').removeClass('hidden');
        })
        .error(function (data) {
          $log.log("projects data" + data);
        });
    };

    $('.projects > ul').addClass('hidden');

    // console.log('before', $scope.json);
    $scope.json = ProjectData.project_data;
    // console.log('after', $scope.json);

    var currentUser = authFactory.getUser();
    //if current user is not set then set again
    
    $scope.selectedProjectID = selectedProjectID;
    $scope.selectedProjectInfo = selectedProjectInfo;

    $scope.modalData = {};
    /**
     * select project for editing
     */
    $scope.selectProject = function (id) {
      /*    if (self.selectedProjectID) {
       $('#' + self.selectedProjectID).removeClass("landing-project-selected");
       }
       $('#' + id).addClass("landing-project-selected");*/
      self.selectedProjectID = id;
      //displayResponseForUser('ProjectCtrl', $scope.getProjectNameByID(self.selectedProjectID));

      for (var i = 0; i < $scope.json.length; ++i) {
        if ($scope.json[i]['id'] == id) {
          ProjectData.setData($scope.json[i]);
        }
      }
      $scope.json = ProjectData.project_data;

      // Setting values for menu builder
      MenuInfo.setProjectId($scope.json['id']);
      MenuInfo.setProjectName($scope.json['name']);

      $location.url('/en-US/project/update/');
    };


    //tabs activate
    $rootScope.tabprojectactive =  true ;
    $scope.tabactivityactive = false;
    $scope.tabnounactive = false;
    $scope.tabmenuactive = false;
        
     $scope.currentTab = function (){
    	 $rootScope.tabprojectactive =  false ;
     }
    
    /**
     * procedure to delete a project
     */
    $scope.deleteProject = function (id) {
      for (var i = 0; i < $scope.json.length; ++i) {
        if ($scope.json[i]['id'] == id) {
          $scope.json = $scope.json[i];
        }
      }

      var jsonForProject = {
        "id": id,
        "name": $scope.json['name'],
        "description": $scope.json['description'],
        "label": $scope.json['label'],
        "default_module_id": 0,
        "default_module_label": "",
        "notes": $scope.json['notes'],
        "createdate": "",
        "createdby": "",
        "lastmodifieddate": "",
        "lastmodifiedby": "",
        "client_os_types": $scope.json['client_os_types'],
        "client_device_types": $scope.json['client_device_types'],
        "client_dev_language": $scope.json['client_dev_languages'],
        "client_dev_framework": $scope.json['client_dev_frameworks'],
        "client_widget_frameworks": $scope.json['client_widget_frameworks'],
        "default_human_language": $scope.project.mainLanguage && $scope.project.mainLanguage['id'] ? $scope.project.mainLanguage['id'] : 2,
        "desktop_css_framework": $scope.desktopCssFramework && $scope.desktopCssFramework['id'] ? $scope.desktopCssFramework['id'] : $scope.defaultTechProperties['desktop_css_framework'],
        "mobile_css_framework": $scope.mobileCssFramework && $scope.mobileCssFramework['id'] ? $scope.mobileCssFramework['id'] : $scope.defaultTechProperties['mobile_css_framework'],
        "app_ui_template": [],
        "client_code_pattern": $scope.clientCodePattern && $scope.clientCodePattern['id'] ? $scope.clientCodePattern['id'] : $scope.defaultTechProperties['client_code_pattern'],
        "server_code_pattern": $scope.serverCodePattern && $scope.serverCodePattern['id'] ? $scope.serverCodePattern['id'] : $scope.defaultTechProperties['server_code_pattern'],
        "server_dev_lang": $scope.serverDevelopmentLanguage && $scope.serverDevelopmentLanguage['id'] ? $scope.serverDevelopmentLanguage['id'] : $scope.defaultTechProperties['server_dev_lang'],
        "server_dev_framework": $scope.serverDevelopmentFramework && $scope.serverDevelopmentFramework['id'] ? $scope.serverDevelopmentFramework['id'] : $scope.defaultTechProperties['server_dev_framework'],
        "server_run_time": $scope.serverRunTime && $scope.serverRunTime['id'] ? $scope.serverRunTime['id'] : $scope.defaultTechProperties['server_run_time'],
        "server_os": $scope.serverOs && $scope.serverOs['id'] ? $scope.serverOs['id'] : $scope.defaultTechProperties['server_os'],
        "server_dbms": $scope.serverDbms && $scope.serverDbms['id'] ? $scope.serverDbms['id'] : $scope.defaultTechProperties['server_dbms'],
        "server_deployment_environment": $scope.serverDepEnviroment && $scope.serverDepEnviroment['id'] ? $scope.serverDepEnviroment['id'] : $scope.defaultTechProperties['server_deployment_environment'],

        "global_extensions": [],
        "ui_navigation_styles": [],
        "supported_browsers": ($scope.getSelectedOptionsForField('supported-browsers').length == 0) ? new Array($scope.defaultTechProperties['supported_browsers'].toString()) : $scope.getSelectedOptionsForField('supported-browsers'),
        "other_human_languages": ($scope.getSelectedOptionsForField('additional-language').length == 0) ? new Array($scope.defaultTechProperties['client_os_types'].toString()) : $scope.getSelectedOptionsForField('additional-language'),
        "entity": $scope.project.projectCompany,
        "enforce_documentation": false,
        "widget_count": 100,
        "generation_type": "100",
        "authorization_type": "group_based",
        "authorizations": [
          "1",
          "2"
        ],
        "project_nouns": null,
        "project_screens": null,
        "processing_mode_ids": null,
        "mobile_standalone": false,
        "cert_required": false,
        "external_module_list": null,
        "default_module": null,
        "multi_user_status": null,
        "multi_user_info": null,
        "application_type":""
      };

      jsonForProject['id'] = id;
      jsonForProject['default_module_id'] = $scope.json['default_module_id'];
      jsonForProject['createdby'] = $scope.json['createdby'];
      jsonForProject['application_type']= $scope.json['application_type'];
      $http.post(RestURL.baseURL + 'project/delete_project/', jsonForProject)
        .success(function (data) {
          $log.log('successfully ' + $routeParams.action + 'd project');
          $location.url("/en-US/projects");

        }).error(function (data) {
        $log.log(data);
      });

    };

    $scope.getProjectNameByID = function (id) {
      if (id == 0) {
        return "Default Project";
      } else {
        for (var i = 0; i < $scope.json.length; ++i) {
          if ($scope.json[i].id == id) {
            return ($scope.json[i].name);
          }
        }
      }
    };

    $scope.submitProject = function () {
    	console.log($scope.defaultTechProperties);
      var jsonForProject = {
        "id": 0,
        "name": $scope.project.projectName,
        "description": $scope.project.projectDescription,
        "label": $scope.project.projectLabel,
        "default_module_id": 0,
        "default_module_label": $scope.project.default_module_label,
        "communication_protocal": $scope.project.communication_protocal,
        "stand_alone_app": $scope.project.stand_alone_app,
        "notes": $scope.project.projectNotes,
        "createdate": "",
        "createdby": "",
        "lastmodifieddate": "",
        "lastmodifiedby": "",
        "client_os_types": $scope.getSelectedOptionsForField($scope.selectedClientOS),
        "client_device_types": $scope.getSelectedOptionsForField($scope.selectedclientdevicetypes), //new Array($scope.defaultTechProperties['client_device_types'].toString()) : $scope.getSelectedOptionsForField('device-type'),
        "client_dev_language": $scope.selectedclientdevlanguage == undefined ? $scope.defaultTechProperties['client_dev_languages'] : $scope.selectedclientdevlanguage,
        "client_dev_framework": $scope.selectedclientdevframework == undefined ? $scope.defaultTechProperties['client_dev_frameworks'] : $scope.selectedclientdevframework,
        "client_widget_frameworks": [], //($scope.getSelectedOptionsForField('widget-frameworks').length == 0) ? $scope.defaultTechProperties['client_widget_frameworks'] : $scope.getSelectedOptionsForField('widget-frameworks'),
        "desktop_css_framework": $scope.project.desktopCssFramework && $scope.project.desktopCssFramework['id'] ? $scope.project.desktopCssFramework['id'] : $scope.defaultTechProperties['desktop_css_framework'],
        "mobile_css_framework": $scope.project.mobileCssFramework && $scope.project.mobileCssFramework['id'] ? $scope.project.mobileCssFramework['id'] : $scope.defaultTechProperties['mobile_css_framework'],
        "app_ui_template": [],
        "client_code_pattern": $scope.project.clientCodePattern && $scope.project.clientCodePattern['id'] ? $scope.project.clientCodePattern['id'] : $scope.defaultTechProperties['client_code_pattern'],
        "server_code_pattern": $scope.project.serverCodePattern && $scope.project.serverCodePattern['id'] ? $scope.project.serverCodePattern['id'] : $scope.defaultTechProperties['server_code_pattern'],
        "server_dev_lang": $scope.project.serverDevelopmentLanguage && $scope.project.serverDevelopmentLanguage['id'] ? $scope.project.serverDevelopmentLanguage['id'] : $scope.defaultTechProperties['server_dev_lang'],
        "server_dev_framework": $scope.project.serverDevelopmentFramework && $scope.project.serverDevelopmentFramework['id'] ? $scope.project.serverDevelopmentFramework['id'] : $scope.defaultTechProperties['server_dev_framework'],
        "server_run_time": $scope.project.serverRunTime && $scope.project.serverRunTime['id'] ? $scope.project.serverRunTime['id'] : $scope.defaultTechProperties['server_run_time'],
        "server_os": $scope.project.serverOs && $scope.project.serverOs['id'] ? $scope.project.serverOs['id'] : $scope.defaultTechProperties['server_os'],
        "server_dbms": $scope.project.serverDbms && $scope.project.serverDbms['id'] ? $scope.project.serverDbms['id'] : $scope.defaultTechProperties['server_dbms'],
        "server_deployment_environment": $scope.project.serverDepEnviroment && $scope.project.serverDepEnviroment['id'] ? $scope.project.serverDepEnviroment['id'] : $scope.defaultTechProperties['server_deployment_environment'],
        "global_extensions": [],
        "ui_navigation_styles": [],
        "supported_browsers": $scope.getSelectedOptionsForField($scope.selectedbrowsers),
        "default_human_language": $scope.project.mainLanguage && $scope.project.mainLanguage['id'] ? $scope.project.mainLanguage['id'] : 2,
        "other_human_languages": $scope.getSelectedOptionsForField($scope.selectedadditionallanguage),
        "entity": $scope.project.projectCompany,
        "enforce_documentation": false,
        "widget_count": 100,
        "generation_type": "100",
        "authorization_type": "group_based",
        "authorizations": [
          "1",
          "2"
        ],
        "project_nouns": null,
        "project_screens": null,
        "processing_mode_ids": null,
        "mobile_standalone": false,
        "cert_required": false,
        "external_module_list": null,
        "default_module": null,
        "multi_user_status": null,
        "multi_user_info": null,
        "application_type":$scope.project.application_type ? $scope.project.application_type : 1,
        "lotus_notes_enabled": $scope.project.lotus_notes_enabled ? $scope.project.lotus_notes_enabled : "N",
        "lotus_notes_cred_enabled": $scope.project.lotus_notes_cred_enabled ? $scope.project.lotus_notes_cred_enabled : "N",
        "extra_project_info": "{}"
      };


      jsonForProject['id'] = 0;
      jsonForProject['default_module_id'] = 0;
      //var msg="Please Wait....Project is under creation!";
      //$scope.pleaseWaitDialog('sm');
      $scope.showWaitingDialog('sm', jsonForProject, "", "create");
//				$timeout(function() {
//				$http.post(RestURL.baseURL + 'project/create_project/', jsonForProject)
//					.success(function(data){
//						var msg='Project Successfully created!';
//						$scope.openSaveDialog('sm',msg);
//						$scope.deleteCookies();
//						$location.url("/en-US/projects");
//					}).error(function(data){
//						$log.log("create project"+data);
//					});
//				}, 3000);

    };

    /**
     *
     */
    $scope.openGenerateModal = function (size) {
      var modalInstance = $modal.open({
        animation: true,
        size: size,
        backdrop: 'static',
        keyboard: false,
        templateUrl: 'app/views/en-US/templates/modals/project/generate.html',
        controller: 'ModalCtrl',
        resolve: {
          data: function () {
            return angular.copy($scope.json["id"]); // deep copy
          }
        }
      });
      modalInstance.result.then(function (dataFromModal) {
        $log.info('Modal dismissed at: ' + new Date());
      }, function () {
        $log.info('Modal dismissed at: ' + new Date());
      });
    };

    /**
     * delete all the cookies
     */
    $scope.deleteCookies = function () {
      $cookieStore.remove("projectName");
      $cookieStore.remove("projectLabel");
      $cookieStore.remove("projectDescription");
      $cookieStore.remove("projectCompany");
      $cookieStore.remove("mainLanguage");
      $cookieStore.remove("additional-language");

    };

    $scope.cancelProject = function () {
      if ($routeParams['action'] == 'create') {
        $scope.deleteCookies();
      }
      $location.url("/en-US/projects/");
    };

   
    
    function emptyAllFields($scope) {
      $scope.project.projectName = "";
      $scope.project.projectLabel = "";
      $scope.project.projectCompany = "";
      $scope.project.projectDescription = "";
      $scope.project.default_module_label = "";
      $scope.project.communication_protocal = "";
      $scope.project.stand_alone_app = "";
      $scope.project.mainLanguage = undefined;
      $scope.project.clientCodePattern = undefined;
      $scope.project.serverCodePattern = undefined;
      $scope.project.application_type = undefined;
      $scope.project.serverDevelopmentLanguage = undefined;
      $scope.project.serverDevelopmentFramework = undefined;
      $scope.project.serverRunTime = undefined;
      $scope.project.serverOs = undefined;
      $scope.project.serverDbms = undefined;
      $scope.project.serverDepEnviroment = undefined;
      $scope.appUiTemplate = undefined;
      $scope.uiNavigationStyle = undefined;
    }

    function emptyAdvanceFields($scope) {
      $scope.project.clientCodePattern = undefined;
      $scope.serverCodePattern = undefined;
      $scope.project.serverDevelopmentLanguage = undefined;
      $scope.project.serverDevelopmentFramework = undefined;
      $scope.project.serverRunTime = undefined;
      $scope.project.serverOs = undefined;
      $scope.project.serverDbms = undefined;
      $scope.project.serverDepEnviroment = undefined;
      $scope.appUiTemplate = undefined;
      $scope.uiNavigationStyle = undefined;
    }

    $scope.project = {};
    $scope.project.projectName = $scope.json['name'];
    $scope.project.projectLabel = $scope.json['label'];
    $scope.project.projectCompany = $scope.json['entity'];
    $scope.project.projectDescription = $scope.json['description'];
    $scope.project.default_module_label = $scope.json['default_module_label'];
    $scope.project.communication_protocal = $scope.json['communication_protocal'];
    $scope.project.stand_alone_app = $scope.json['stand_alone_app'];
    $scope.project.notes = $scope.json['notes'];
    $scope.project.lotus_notes_enabled = $scope.json['lotus_notes_enabled'] == 'Y' ? true:false;
    $scope.project.lotus_notes_cred_enabled = $scope.json['lotus_notes_cred_enabled'] == 'Y' ? true:false;
    $scope.project.extra_project_info = $scope.json['extra_project_info'] ? JSON.parse($scope.json['extra_project_info']):{};

    var mainLanguages = [];

    $scope.techProperties = [];
    $scope.serverLanguages = [];

    $scope.serverDevFramework = [];
    $scope.serverRunTimes = [];
    $scope.serverOS = [];
    $scope.serverDBMS = [];
    $scope.serverCodePatterns = [];
    $scope.serverDeploymentEnvironment = [];
    $scope.clientCodePatterns = [];
    $scope.desktopCssFrameworks = [];
    $scope.mobileCssFrameworks = [];
    $scope.clientWidgetFramework = [];

    $scope.activities = [];
    $scope.nouns = [];
    $scope.other_nouns = [];
    $scope.languages = [];
    $scope.tempaddlanguages = [];
    $scope.mainLanguages = [];
    $scope.projectTemplates = [];

    $scope.additionallanguages = [];
    $scope.selectedadditionallanguage = [];

    $scope.clientdevlanguage = [];
    $scope.selectedclientdevlanguage;

    $scope.clientOS = [];
    $scope.selectedClientOS = [];

    $scope.clientdevframework = [];
    $scope.selectedclientdevframework;

    $scope.clientDeviceTypes = [];
    $scope.selectedclientdevicetypes = [];

    $scope.browsers = [];
    $scope.selectedbrowsers = [];

    $scope.defaultTechProperties = {};

    $scope.project.projectNotes = "";
    if ($scope.json['notes'] != '') {
      $scope.project.projectNotes = $scope.json['notes'];
    }
    $scope.additionallanguage = [];
    $scope.appUiTemplate = $scope.json['app_ui_template'];
    $scope.uiNavigationStyle = $scope.json['ui_navigation_styles'];

    //getting all langauges
    $scope.getAllPrimaryLanguages = function () {
      $http.get(RestURL.baseURL + 'language/get_all_languages/')
        .success(function (data) {
          for (var i = 0; data[i]; ++i) {
            var lng = {
              id: data[i]['id'],
              iso_id: data[i]['iso_id'],
              name: data[i]['ref_name']
            };
            /*var addlng = {
             id : data[i]['id'],
             label : data[i]['ref_name']
             };*/

            $scope.languages.push(lng);
            //$scope.tempaddlanguages.push(addlng);
          }
          languageFactory.setData(data);
          $scope.mainLanguages = $scope.languages;
          //$scope.additionalLanguages= $scope.tempaddlanguages;
          if ($routeParams['action'] == 'update') {
            fillInfo($scope);
          }

        }).error(function (data) {
        $log.log("lang data" + data);
      });
    };

    $scope.getAllSecondaryLanguages = function (selectedPrimaryLanguage) {
      $http.get(RestURL.baseURL + 'language/get_all_languages/')
        .success(function (data) {
          $scope.tempaddlanguages = [];
          for (var i = 0; data[i]; ++i) {
            var addlng = {
              id: data[i]['id'],
              label: data[i]['ref_name']
            };
            if (selectedPrimaryLanguage != data[i]['ref_name'] && selectedPrimaryLanguage != data[i]['id']) {
              $scope.tempaddlanguages.push(addlng);
            }
          }
          languageFactory.setData(data);
          $scope.additionalLanguages = $scope.tempaddlanguages;
          if ($routeParams['action'] == 'update') {
            fillInfo($scope);
          }

        }).error(function (data) {
        $log.log("lang data" + data);
      });
    };
    
    $scope.getApplicationType =function(){
    	$http.get(RestURL.baseURL + 'project/find_all_Application_type/')
        .success(function (data) {
        	$scope.Apptype=data;
        }).error(function (data) {
            $log.log("Application Types" + data);
        });
    }
    
    $scope.getApplicationType();

    //getting all techProperties
    $scope.getAllTechProperties = function () {
      $http.get(RestURL.baseURL + 'techproperties/get_all_tech_properties/')
        .success(function (data) {
          $scope.techProperties = data;
          for (var i = 0; i < $scope.techProperties.length; ++i) {
            var obj = data[i];
            var objtouse = {
              id: data[i]['id'],
              label: data[i]['name']
            };

            switch (obj["type"]) {
              case "GpServerLanguage":
                $scope.serverLanguages.push(obj);

                break;
              case "GpClientLanguage":
                $scope.clientdevlanguage.push(objtouse);

                break;
              case "GpBrowser":
                $scope.browsers.push(objtouse);

                break;
              case "GpServerDevFramework":
                $scope.serverDevFramework.push(obj);

                break;
              case "GpServerRunTime":
                $scope.serverRunTimes.push(obj);

                break;
              case "GpServerOS":
                $scope.serverOS.push(obj);

                break;
              case "GpServerCodePattern":
                $scope.serverCodePatterns.push(obj);

                break;
              case "GpServerDeploymentEnvironment":
                $scope.serverDeploymentEnvironment.push(obj);

                break;
              case "GpClientCodePattern":
                $scope.clientCodePatterns.push(obj);

                break;
              case "GpDesktopCssFramework":
                $scope.desktopCssFrameworks.push(obj);

                break;
              case "GpMobileCssFramework":
                $scope.mobileCssFrameworks.push(obj);

                break;
              case "GpClientOS":
                $scope.clientOS.push(objtouse);

                break;
              case "GpServerDBMS":
                $scope.serverDBMS.push(obj);

                break;
              case "GpClientDevFramework":
                $scope.clientdevframework.push(objtouse);

                break;
            }
          }

        }).error(function (data) {
        $log.log("tech prop" + data);
      });
    };

    // getting device types
    $scope.getAllDeviceTypes = function () {
      $http.get(RestURL.baseURL + 'devicetypes/find_all_devices/')
        .success(function (data) {
          for (var i = 0; data[i]; ++i) {
            var obj = {
              id: data[i]['id'],
              label: data[i]['client_device_type_label']
            };
            $scope.clientDeviceTypes.push(obj);
          }
          $scope.getAllPrimaryLanguages();
          $scope.getAllTechProperties();
        })
        .error(function (data) {
          $log.log("device type" + data);
        });
    };

    $scope.getAllDeviceTypes();


    // open add project modal
    $scope.openAddProjectModal = function () {
      var tempdata = {"main": $scope.mainLanguages, "additional": $scope.additionalLanguages};
      var modalInstance = $modal.open({
        animation: true,
        templateUrl: 'app/views/en-US/templates/modals/project/create.html',
        controller: 'ModalCtrl',
        resolve: {
          data: function () {
            return angular.copy(tempdata); // deep copy
          }
        }
      });

      modalInstance.result.then(function (dataFromModal) {
        $scope.modalData = dataFromModal;
        //populate scope vars with modal data
        $scope.project.projectName = $scope.modalData.projectName;
        $scope.project.projectDescription = $scope.modalData.projectDescription;
        $scope.project.projectLabel = $scope.modalData.projectLabel;
        $scope.project.projectCompany= $scope.modalData.projectCompany;
        $scope.project.mainLanguage = $scope.modalData.mainLanguage;
        $scope.selectedadditionallanguage = $scope.modalData.selectedadditionallanguages;

        if ($scope.modalData.action == "create") {
          $scope.submitProject();
        } else {
          $cookieStore.put('projectName', $scope.project.projectName);
          $cookieStore.put('projectLabel', $scope.project.projectLabel);
          $cookieStore.put('projectDescription', $scope.project.projectDescription);
          $cookieStore.put('projectCompany', $scope.project.projectCompany);

          $cookieStore.put('additionalLanguage', $scope.selectedadditionallanguage);

          if ($scope.project.mainLanguage != undefined)
            $cookieStore.put('mainLanguage', $scope.project.mainLanguage);
          else
            $cookieStore.put('mainLanguage', '');

          $location.url('/en-US/project/create/');
        }

      }, function () {
        $log.info('Modal dismissed at: ' + new Date());
      });
    };

    $scope.multiselectsettings = {
      scrollableHeight: '200px',
      scrollable: true
    };
    /*$scope.pleaseWaitDialog = function (size) {
     var modalInstance = $modal.open({
     animation: true,
     size:size,
     templateUrl: 'app/views/en-US/templates/modals/project/please_wait.html',
     controller: 'ModalCtrl',
     resolve: {
     data: function () {
     return size; // deep copy
     },
     }
     });
     modalInstance.result.then(function (dataFromModal) {
     alert("HII");
     }, function () {
     alert("f");
     });
     };*/

    $scope.openSaveDialog = function (size, msg) {
      var modalInstance = $modal.open({
        animation: true,
        size: size,
        templateUrl: 'app/views/en-US/templates/modals/project/savemessage.html',
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

    $scope.showWaitingDialog = function (size, jsonForProject, projectId, action) {
      var data = {
        jsonForProject: jsonForProject,
        action: action,
        projectId: projectId
      };

      var modalInstance = $modal.open({
        animation: true,
        size: size,
        templateUrl: 'app/views/en-US/templates/modals/project/please_wait.html',
        controller: 'ModalCtrl',
        resolve: {
          data: function () {
            return data; // deep copy
          }
        }
      });

      modalInstance.result.then(function (dataFromModal) {
        $scope.modalData = dataFromModal;
        var msg = $scope.modalData.postSuccessMsg;
        $scope.openSaveDialog('sm', msg);
        if ($scope.modalData.action == "add_module") {
          $scope.deleteCookies();
          $location.url("/en-US/project/update");
          $scope.getAllPredefinedActivities();
        }
        if ($scope.modalData.action == "create") {
          $scope.deleteCookies();
          $location.url("/en-US/projects");
        }
        if ($scope.modalData.action == "update") {
          // $scope.deleteCookies();
          //$location.url("/en-US/projects");
        }
      }, function () {
        $scope.deleteCookies();
        $log.info('Modal dismissed at: ' + new Date());
      });
    };

    $scope.openDeleteDialog = function (size, id) {
      var modalInstance = $modal.open({
        animation: true,
        size: size,
        templateUrl: 'app/views/en-US/templates/modals/project/delete.html',
        controller: 'ModalCtrl',
        resolve: {
          data: function () {
            return angular.copy(id); // deep copy
          }
        }
      });
      modalInstance.result.then(function (dataFromModal) {
        $scope.deleteProject(dataFromModal);
      }, function () {
        $log.info('Modal dismissed at: ' + new Date());
      });
    };

    //selecting options for update screen
    $scope.setValueForSelect = function () {
      if ($scope.project.mainLanguage == undefined && $scope.json['default_human_language'] == 0) {
        $scope.broswerLanguageDetection();
      }
      if ($scope.project.mainLanguage == undefined && $scope.json['default_human_language'] != 0) {
        $scope.project.mainLanguage = {id: Number($scope.json['default_human_language'])};
        $scope.additionalLanguageDropdown($scope.project.mainLanguage.id);
      }

      // $scope.project.mainLanguage = { id : Number($scope.json['default_human_language']) };
      console.log($scope.json);
      $scope.selectedclientdevlanguage = {id: Number($scope.json['client_dev_language'])};
      $scope.selectedclientdevframework = {id: Number($scope.json['client_dev_framework'])};
      $scope.project.clientCodePattern = {id: Number($scope.json['client_code_pattern'])};
      $scope.project.serverCodePattern = {id: Number($scope.json['server_code_pattern'])};
      $scope.project.serverDevelopmentLanguage = {id: Number($scope.json['server_dev_lang'])};
      $scope.project.application_type ={id: Number($scope.json['application_type'])};
      $scope.project.serverDevelopmentFramework = {id: Number($scope.json['server_dev_framework'])};
      $scope.project.serverRunTime = {id: Number($scope.json['server_run_time'])};
      $scope.project.desktopCssFramework = {id: Number($scope.json['desktop_css_framework'])};
      $scope.project.mobileCssFramework = {id: Number($scope.json['mobile_css_framework'])};
      $scope.project.serverOs = {id: Number($scope.json['server_os'])};
      $scope.project.serverDbms = {id: Number($scope.json['server_dbms'])};
      $scope.project.serverDepEnviroment = {id: Number($scope.json['server_deployment_environment'])};
    };

    $scope.setValuesForMultiselect = function (selectedOptions) {
      if (selectedOptions != undefined) {
        var selectedmodal = '[';
        var delim = ',';
        for (var i = 0; i < selectedOptions.length; i++) {
          if (i == selectedOptions.length - 1) {
            delim = '';
          }
          if (selectedOptions[i] != '')
            selectedmodal += "{'id':" + selectedOptions[i] + "}" + delim;
        }
        selectedmodal += ']';
        return selectedmodal;
      }
    };

    function fillInfo($scope) {
      //putting project values in fields
      $scope.project.projectName = $scope.json['name'];
      $scope.project.projectLabel = $scope.json['label'];
      $scope.project.projectCompany = $scope.json['entity'];
      $scope.project.projectDescription = $scope.json['description'];
      $scope.project.default_module_label = $scope.json['default_module_label'];
      $scope.project.communication_protocal = $scope.json['communication_protocal'];
      $scope.project.stand_alone_app = $scope.json['stand_alone_app'];
      $scope.project.notes = $scope.json['notes'];


      //calling method for Selects
      $scope.setValueForSelect();
      $scope.selectedmodal = [];
      $scope.selectedmodal = $scope.setValuesForMultiselect($scope.json['other_human_languages']);
      $scope.selectedadditionallanguage = eval($scope.selectedmodal);

      $scope.selectedmodal = [];
      $scope.selectedmodal = $scope.setValuesForMultiselect($scope.json['client_os_types']);
      $scope.selectedClientOS = eval($scope.selectedmodal);

      $scope.selectedmodal = [];
      $scope.selectedmodal = $scope.setValuesForMultiselect($scope.json['client_device_types']);
      $scope.selectedclientdevicetypes = eval($scope.selectedmodal);

      $scope.selectedmodal = [];
      $scope.selectedmodal = $scope.setValuesForMultiselect($scope.json['client_widget_frameworks']);
      $scope.selectedclientdevicetypes = eval($scope.selectedmodal);

      $scope.selectedmodal = [];
      $scope.selectedmodal = $scope.setValuesForMultiselect($scope.json['supported_browsers']);
      $scope.selectedbrowsers = eval($scope.selectedmodal);
    }

    $scope.getDefaultProperties = function () {
      $http.get(RestURL.baseURL + 'techproperties/get_default_properties/')
        .success(function (data) {
          for (var i = 0; i < data.length; ++i) {
            var obj = data[i];
            switch (obj["type"]) {
              case "GpServerLanguage":
                if (data[i]['default_value']) {
                  $scope.defaultTechProperties['server_dev_lang'] = data[i]['id'];
                }

                break;
              case "GpClientLanguage":
                if (data[i]['default_value']) {
                  $scope.defaultTechProperties['client_dev_languages'] = data[i]['id'];
                }

                break;
              case "GpBrowser":
                if (data[i]['default_value']) {
                  $scope.defaultTechProperties['supported_browsers'] = data[i]['id'];
                }

                break;
              case "GpServerDevFramework":
                if (data[i]['default_value']) {
                  $scope.defaultTechProperties['server_dev_framework'] = data[i]['id'];
                }

                break;
              case "GpServerRunTime":
                if (data[i]['default_value']) {
                  $scope.defaultTechProperties['server_run_time'] = data[i]['id'];
                }

                break;
              case "GpServerOS":
                if (data[i]['default_value']) {
                  $scope.defaultTechProperties['server_os'] = data[i]['id'];
                }

                break;
              case "GpServerCodePattern":
                if (data[i]['default_value']) {
                  $scope.defaultTechProperties['server_code_pattern'] = data[i]['id'];
                }

                break;
              case "GpServerDeploymentEnvironment":
                if (data[i]['default_value']) {
                  $scope.defaultTechProperties['server_deployment_environment'] = data[i]['id'];
                }

                break;
              case "GpClientCodePattern":
                if (data[i]['default_value']) {
                  $scope.defaultTechProperties['client_code_pattern'] = data[i]['id'];
                }

                break;
              case "GpDesktopCssFramework":
                if (data[i]['default_value']) {
                  $scope.defaultTechProperties['desktop_css_framework'] = data[i]['id'];
                }

                break;
              case "GpMobileCssFramework":
                if (data[i]['default_value']) {
                  $scope.defaultTechProperties['mobile_css_framework'] = data[i]['id'];
                }

                break;
              case "GpClientOS":
                if (data[i]['default_value']) {
                  $scope.defaultTechProperties['client_os_types'] = data[i]['id'];
                }

                break;
              case "GpServerDBMS":
                if (data[i]['default_value']) {
                  $scope.defaultTechProperties['server_dbms'] = data[i]['id'];
                }

                break;
              case "GpClientDevFramework":
                if (data[i]['default_value']) {
                  $scope.defaultTechProperties['client_dev_frameworks'] = data[i]['id'];
                }

                break;
            }
          }
        }).error(function (data) {
        $log.log(data);
      });
    };

    $scope.getDefaultProperties();

    $scope.openAbout = function () {
      $location.url('/en-US/about/');
    };

    //getting all activities for current project
    $scope.getAllActivities = function ($scope, projectID) {
      $log.log("get all activities called!");
      $scope.activities = [];
      $http.get(RestURL.baseURL + 'activity/get_all_activities_by_project_id/?project_id=' + projectID)
        .success(function (data) {
        	$log.log("data of all activities"+angular.toJson(data));
        	
          for (var index = 0; index < data.length; index++) {
        	  var actObj = data[index];
            var actObj1 = data[index].name;
            $rootScope.actobj1.push(data[index].name);
            
            $scope.activities.push(actObj);
          }
          $log.log("data in act0bj1"+$rootScope.actobj1);
          $scope.getAllPredefinedActivities();
        }).error(function (data) {
        $log.log(data);
      });
    };

    // getting all other activities
    $scope.predefined_activities_other = [];
    $scope.predefined_activities_system = [];
    $scope.predefined_activity = [];

    $scope.getAllPredefinedActivities = function () {
      $log.log("get all predefined activities called!");
      $http.get(RestURL.baseURL + 'activity/get_all_predefined_activities')
        .success(function (data) {
          for (var index = 0; index < $scope.activities.length; index++) {
            var activity_id = $scope.activities[index].predefined_activity_id;
            $scope.predefined_activity.push(activity_id);
          }
          for (var actIndex = 0; actIndex < data.length; actIndex++) {
            for (var preactIndex = 0; preactIndex < $scope.predefined_activity.length; preactIndex++) {
              if (data[actIndex].id == $scope.predefined_activity[preactIndex]) {
                data[actIndex].activity_visibility = false;
                break;
              }
            }
            if (data[actIndex].activity_type != null && data[actIndex].activity_type == 'GpSystemLevel') {
              $scope.predefined_activities_system.push(data[actIndex]);
            } else {
              $scope.predefined_activities_other.push(data[actIndex]);
            }
          }
        }).error(function (data) {
        $log.log(data);
      });
    };

    /**
     * used to get all nouns based on project
     */
    function getAllNounsBasedOnProject($scope, projectID) {
      $http.get(RestURL.baseURL + 'noun/get_all_nouns_by_project_id/?project_id=' + projectID
      ).success(function (response) {
        $scope.nouns = response;
      }).error(function (response) {
        $log.log('Error : ', response);
      });
    }

    //getting all projectTemplates for current project
    $scope.getAllProjectTemplates = function ($scope, projectID) {
      //$log.log("get all project templates called!");
      $http.get(RestURL.baseURL + 'projecttemplate/get_by_project/?projectId=' + projectID)
        .success(function (data) {
          $scope.projectTemplates = data;
        }).error(function (data) {
        //console.log("project.js -> getAllProjectTemplates -> ERROR: " + JSON.stringify( data ) )
        $log.log(data);
      });
    };


    //getting all Project Templates Devices

    $scope.getDeviceClass = function (device) {
      if (device == 'PC')
        return "fa fa-desktop";
      else if (device == 'Tablet')
        return "fa fa-tablet";
      else if (device == 'Mobile')
        return "fa fa-mobile";
      else if (device == null && device == '')
        return "";
    };


    var values = [];
    // This method is only for multiselect fields
    $scope.getSelectedOptionsForField = function (fieldID) {
      values = [];

      var options = fieldID;
      for (var i = 0; i < options.length; i++) {
        values.push(options[i].id);
      }
      return values;
    };

    $scope.updateActivity = function (activityId, activityName) {
      ActivityInfo.setActivityId(activityId);
      ActivityInfo.setProjectId($scope.json["id"]);
      ActivityInfo.setActivityName(activityName);
      $location.url('/en-US/activity/update');
    };

    $scope.updateProjectTemplate = function (projectTemplateId, device) {
      ActivityInfo.setProjectId($scope.json["id"]);
      ActivityInfo.setActivityId(projectTemplateId);
      ActivityInfo.setDeviceTypes(device);
      $location.url('/en-US/projecttemplate/update');
    };

    $scope.createActivity = function () {
      ActivityInfo.setProjectId($scope.json["id"]);

      $location.url('/en-US/activity/create');
    };

    $scope.showcreate = false;

    switch ($routeParams['action']) {
      case undefined:
        $cookieStore.put('back', '/en-US/projects/');
        $scope.getProjectsForUser();
        emptyAllFields($scope);

        break;
      case 'create':
        $scope.showcreate = true;
        $cookieStore.put('back', '/en-US/project/create/');
        $scope.project.projectName = $cookieStore.get('projectName');
        $scope.project.projectLabel = $cookieStore.get('projectLabel');
        $scope.project.projectDescription = $cookieStore.get('projectDescription');
        $scope.project.projectCompany = $cookieStore.get('projectCompany');

        if ($cookieStore.get('mainLanguage') != undefined)
          $scope.project.mainLanguage = {id: $cookieStore.get('mainLanguage')['id']};

        if ($cookieStore.get('additionalLanguage') != undefined)
          $scope.selectedadditionallanguage = $cookieStore.get('additionalLanguage');

        emptyAdvanceFields($scope);
        $scope.getAllPredefinedActivities();

        break;
      case 'update':
        $scope.showcreate = false;
        $cookieStore.put('back', '/en-US/project/update/');
        $scope.getAllActivities($scope, $scope.json['id']);
        $scope.getAllProjectTemplates($scope, $scope.json['id']);
        getAllNounsBasedOnProject($scope, $scope.json['id']);

        break;
      default :
        //TODO add error page
        $location.url('/en-US/login/');
    }
    
    $scope.test = function(selectedclientdevframework) {
    	
    	$scope.selectedclientdevframework = selectedclientdevframework;
    	console.log("selected framework",$scope.selectedclientdevframework);
    	
    }

    $scope.updateProject = function () {
    	console.log("11111111" + $scope.project.projectCompany);
    	console.log($scope.selectedclientdevframework);
      var jsonForProject = {
        "id": 0,
        "name": $scope.project.projectName,
        "description": $scope.project.projectDescription,
        "label": $scope.project.projectLabel,
        "default_module_id": 0,
        "default_module_label": $scope.project.default_module_label,
        "communication_protocal": $scope.project.communication_protocal,
        "stand_alone_app": $scope.project.stand_alone_app,
        "notes": $scope.project.projectNotes,
        "createdate": "",
        "createdby": "",
        "lastmodifieddate": "",
        "lastmodifiedby": "",
        "client_os_types": $scope.getSelectedOptionsForField($scope.selectedClientOS),
        "client_device_types": $scope.getSelectedOptionsForField($scope.selectedclientdevicetypes), //new Array($scope.defaultTechProperties['client_device_types'].toString()) : $scope.getSelectedOptionsForField('device-type'),
        "client_dev_language": $scope.selectedclientdevlanguage == undefined ? $scope.defaultTechProperties['client_dev_languages'] : $scope.selectedclientdevlanguage['id'],
        "client_dev_framework": $scope.selectedclientdevframework == undefined ? $scope.defaultTechProperties['client_dev_frameworks'] : $scope.selectedclientdevframework['id'],
        "client_widget_frameworks": [], //($scope.getSelectedOptionsForField('widget-frameworks').length == 0) ? $scope.defaultTechProperties['client_widget_frameworks'] : $scope.getSelectedOptionsForField('widget-frameworks'),
        "desktop_css_framework": $scope.project.desktopCssFramework && $scope.project.desktopCssFramework['id'] ? $scope.project.desktopCssFramework['id'] : $scope.defaultTechProperties['desktop_css_framework'],
        "mobile_css_framework": $scope.project.mobileCssFramework && $scope.project.mobileCssFramework['id'] ? $scope.project.mobileCssFramework['id'] : $scope.defaultTechProperties['mobile_css_framework'],
        "app_ui_template": [],
        "client_code_pattern": $scope.project.clientCodePattern && $scope.project.clientCodePattern['id'] ? $scope.project.clientCodePattern['id'] : $scope.defaultTechProperties['client_code_pattern'],
        "server_code_pattern": $scope.project.serverCodePattern && $scope.project.serverCodePattern['id'] ? $scope.project.serverCodePattern['id'] : $scope.defaultTechProperties['server_code_pattern'],
        "server_dev_lang": $scope.project.serverDevelopmentLanguage && $scope.project.serverDevelopmentLanguage['id'] ? $scope.project.serverDevelopmentLanguage['id'] : $scope.defaultTechProperties['server_dev_lang'],
        "server_dev_framework": $scope.project.serverDevelopmentFramework && $scope.project.serverDevelopmentFramework['id'] ? $scope.project.serverDevelopmentFramework['id'] : $scope.defaultTechProperties['server_dev_framework'],
        "server_run_time": $scope.project.serverRunTime && $scope.project.serverRunTime['id'] ? $scope.project.serverRunTime['id'] : $scope.defaultTechProperties['server_run_time'],
        "server_os": $scope.project.serverOs && $scope.project.serverOs['id'] ? $scope.project.serverOs['id'] : $scope.defaultTechProperties['server_os'],
        "server_dbms": $scope.project.serverDbms && $scope.project.serverDbms['id'] ? $scope.project.serverDbms['id'] : $scope.defaultTechProperties['server_dbms'],
        "server_deployment_environment": $scope.project.serverDepEnviroment && $scope.project.serverDepEnviroment['id'] ? $scope.project.serverDepEnviroment['id'] : $scope.defaultTechProperties['server_deployment_environment'],
        "global_extensions": [],
        "ui_navigation_styles": [],
        "supported_browsers": $scope.getSelectedOptionsForField($scope.selectedbrowsers),
        "default_human_language": $scope.project.mainLanguage && $scope.project.mainLanguage['id'] ? $scope.project.mainLanguage['id'] : 2,
        "other_human_languages": $scope.getSelectedOptionsForField($scope.selectedadditionallanguage),
        "entity": $scope.project.projectCompany,
        "enforce_documentation": false,
        "widget_count": 100,
        "generation_type": "100",
        "authorization_type": "group_based",
        "authorizations": [
          "1",
          "2"
        ],
        "project_nouns": null,
        "project_screens": null,
        "processing_mode_ids": null,
        "mobile_standalone": false,
        "cert_required": false,
        "external_module_list": null,
        "default_module": null,
        "multi_user_status": null,
        "multi_user_info": null,
        "application_type":$scope.project.application_type.id,
        "lotus_notes_enabled":$scope.project.lotus_notes_enabled ? 'Y':'N',
        "lotus_notes_cred_enabled":$scope.project.lotus_notes_cred_enabled ? 'Y':'N',
        "extra_project_info": JSON.stringify($scope.project.extra_project_info)
      };
      console.log(jsonForProject);
      jsonForProject['id'] = $scope.json['id'];
      jsonForProject['default_module_id'] = $scope.json['default_module_id'];
      jsonForProject['createdby'] = $scope.json['createdby'];
      //var msg="Please Wait....Project is getting updated!";
      //$scope.showWaitingDialog('sm',jsonForProject,$routeParams.action);
      $scope.showWaitingDialog('sm', jsonForProject, "", $routeParams.action);
      /*$scope.pleaseWaitDialog('sm');
       $timeout(function() {
       $http.post(RestURL.baseURL + 'project/update_project/', jsonForProject)
       .success(function(data){
       var msg='Project Successfully ' + $routeParams.action + 'd !';
       $scope.openSaveDialog('sm',msg);

       }).error(function(data){
       $log.log(data);
       });
       }, 3000);*/

    };

    $scope.addModule = function (module, projId) {
      $scope.showWaitingDialog('sm', module, projId, "add_module");
      /*$scope.pleaseWaitDialog('sm');
       var msg='Module Successfully added!';
       var url = RestURL.baseURL + 'module/add_module/';
       $timeout(function() {
       $http({
       method : "POST",
       url : url,
       data : {
       name: module.name,
       label: module.label,
       description: module.description,
       projectid: $scope.json['id'],
       base_location: module.location_path,
       predefined_activity_id: module.id
       },
       headers : {
       "content-type" : "application/json",
       "Accept" : "application/json"
       },
       }).success(function(data){
       var msg='Module Successfully added!';
       $scope.openSaveDialog('sm',msg);
       $scope.deleteCookies();
       $location.url("/en-US/project/update");
       $scope.getAllPredefinedActivities();
       }).error(function(data){
       alert("error");
       $log.log("add module"+data);
       });
       }, 3000);*/
    };

    /**
     * activity creation
     */
    $scope.activity = {
      "id": 0,
      "name": "",
      "activity_types": [],
      "projectid": $scope.json['id'],
      "moduleid": $scope.json['default_module_id'],
      "primary_noun": {},
      "secondary_nouns": [],
      "tablet_screens": [],
      "phone_screens": [],
      "pc_screens": [],
      "notes": "<div><br></div>",
      "label": "",
      "description": "",
      "module_type": ""
    };

    /**
     * creating new activity
     */
    $scope.createActivityNow = function () {
      $log.log("creating new activity!");


      if (!$scope.checkProjectIsExists($scope.activity)) {
        return;
      }

      $http({
        url: RestURL.baseURL + 'activity/create_activity/',
        data: angular.toJson($scope.activity),
        method: 'POST',
        headers: {
          'Accept': 'application/json'
        }
      })

        .success(function (response) {
          if (response != '') {
            $scope.activity = {
              "id": 0,
              "name": "",
              "activity_types": [],
              "projectid": $scope.json['id'],
              "moduleid": $scope.json['default_module_id'],
              "primary_noun": {},
              "secondary_nouns": [],
              "tablet_screens": [],
              "phone_screens": [],
              "pc_screens": [],
              "notes": "<div><br></div>",
              "label": "",
              "description": ""
            };

            $scope.activities.push(response);
            $scope.$broadcast("activityChangeEvent", "");
            /**This code is used to close the modal*/
            instance.close('dismiss');
          }else{
        	  /**This code is used to show the message exist or not. it binds the message in html file that project_an.html*/
        	  console.log("$$ This name is already exist in the table. try to use another name please.") 
        	  $scope.myForm.name.$setValidity("ntValidName", false); 
        	 /** time to dispaly message. it will be neccessary. for that i put the code here**/
        	  /** $timeout(function(){
        		  $scope.myForm.name.$setValidity("ntValidName", true);
               }, 10000);*/
          }
          
          
        })
        .error(function (response) {
        });
    };


    $scope.deleteActivityObj = {};


    $scope.setDeleteActivityObj = function (activityObj) {
      $log.log('setting delete obj for activity');
      $scope.deleteActivityObj = activityObj;
    };

    /**
     * delete activity
     */
    $scope.deleteActivity = function () {
      $log.log('delete activity has been called!', $scope.deleteActivityObj);

      $http({
        url: RestURL.baseURL + 'activity/delete_activity/',
        data: angular.toJson($scope.deleteActivityObj),
        method: 'POST',
        headers: {
          'Accept': 'application/json'
        }
      })
        .success(function (response) {

          $log.debug('deleting activity!');
          var delIdx = -1;
          for (var idx in $scope.activities) {
            if ($scope.activities[idx].id == $scope.deleteActivityObj.id) {
              delIdx = idx;
              break;
            }
          }

          if (delIdx > -1) {
            $scope.activities.splice(delIdx, 1);
          }

          $scope.$broadcast("activityChangeEvent", "");

          $scope.deleteCookies();
          $location.url("/en-US/project/update");
          $scope.getAllPredefinedActivities();
        })
        .error(function (response) {
        });

    };

    $scope.deleteProjectTemplateObj = {};

    $scope.setDeleteProjectTemplate = function (projectTemplateObj) {
      $log.log('setting delete obj for project template');
      $scope.deleteProjectTemplateObj = projectTemplateObj;
    };

    /**
     * delete activity
     */
    $scope.deleteProjectTemplate = function () {
      $log.log('delete project template  has been called!', $scope.deleteProjectTemplateObj);

      $http.get(RestURL.baseURL + 'projecttemplate/delete/?id=' + $scope.deleteProjectTemplateObj.projectTemplateId)
        .success(function (response) {
          $log.debug('deleting project template!');
          var delIdx = -1;
          for (var idx in $scope.projectTemplates) {
            if ($scope.projectTemplates[idx].projectTemplateId == $scope.deleteProjectTemplateObj.projectTemplateId) {
              delIdx = idx;
              break;
            }
          }
          if (delIdx > -1) {
            $scope.projectTemplates.splice(delIdx, 1);
          }
          $scope.$broadcast("activityChangeEvent", "");
          $scope.deleteCookies();
          $location.url("/en-US/project/update");
          //$scope.getAllPredefinedActivities();
        })
        .error(function (response) {
        });
    };

    /**
     * project template creation
     */
    $scope.projectTemplate = {
      "projectId": $scope.json['id'],
      "name": "",
      "label": "",
      "description": "",
      "device": ""
    };

    /**
     * creating new project template
     */
    $scope.createProjectTemplateNow = function () {
      $log.log("creating new project template!");

      if (!$scope.checkProjectIsExists($scope.projectTemplate)) {
        return;
      }
      $http({
        url: RestURL.baseURL + 'projecttemplate/create/',
        data: angular.toJson($scope.projectTemplate),
        method: 'POST',
        headers: {
          'Accept': 'application/json'
        }
      })
        .success(function (response) {
          if (response != null) {
            $scope.projectTemplate = {
              "projectId": $scope.json['id'],
              "name": "",
              "label": "",
              "description": "",
              "device": ""
            };
            $scope.projectTemplates.push(response);
            $scope.$broadcast("templateChangeEvent", "");
          }
        })
        .error(function (response) {
        });
    };

    $scope.noun = {
      "technicalname": "",
      "activitiesusedin": "",
      "cacheenabled": "",
      "cacheable": true,
      "parents": [],
      "children": [],
      "nounattributes": [],
      "name": "",
      "description": "",
      "projectid": $scope.json['id'],
      "moduleid": $scope.json['default_module_id'],
      "label": "",
      "notes": ""
    };

    /**
     * create noun
     */
    
    $scope.get_mongo_noun = function (selected_db){
    	console.log("======ONLY OBJECT===>",selected_db);
    	console.log("======DHDIDIDDHDIDHIDHDHID===>",JSON.stringify(selected_db));
         $http({
             url: RestURL.baseURL + 'noun/get_mongo_noun/',
             data: selected_db,
             method: 'POST',
             headers: {
               'Accept': 'application/json'
             }
           })
          .success(function (response) {
           console.log("data=====DHIANIANAIAI====>",response)
           $scope.list_of_mongo_nouns = response;x
           })
          .error(function (response) {
            
          });
    }
    
    $scope.get_couch_design = function (selected_design){
    	console.log("design "+ angular.toJson(selected_design))
    	//ajax for views
      	console.log("success")
   	   $scope.isDisabled = true;
    	console.log("inside success of get_all_couch_views")
       	$http.get(RestURL.baseURL + 'noun/get_all_couch_views/'+selected_design.design)
           .success(function (data) {
           	console.log(angular.toJson(data));
           	$scope.couch_views = data;
           }).error(function(data){
        	   console.log(data);
           });
    	
    	
    }
    $scope.get_couch_views = function (selected_views){
    	
    	console.log("data====views=====>",angular.toJson(selected_views));
    	     
    	 $http({
             url: RestURL.baseURL + 'noun/get_couch_noun/',
             data: angular.toJson(selected_views),
             method: 'POST',
             headers: {
               'Accept': 'application/json'
             }
           })	
          .success(function (data) {
           console.log("data===vies respnse ======>",data.attribute)
           $scope.list_of_mongo_nouns = data;
           })
          .error(function (data) {
            
          });
    	
    	
    	
    }
    
    //couch db noun
    $scope.get_couch_noun = function (selected_db){
    	$log.log("selected_db "+angular.toJson(selected_db.bucket))
    	
      //ajax for design
        $log.log("success")
 	   $scope.isDisabled = true;
     	$log.log("inside success of get_all_couch_design")
     	$http.get(RestURL.baseURL + 'noun/get_all_couch_design/'+selected_db.bucket)
         .success(function (data) {
         	console.log(angular.toJson(data));
         	$scope.couch_desing = data;
         }).error(function(data){
       	  $log.log(data);
         })	
        
    }
    // Storing the Wsdl link method
    $scope.link = {};
    $scope.addWsdlLink = function(link){
//    alert('hi')	
//    	$scope.link = angular.copy(link);
    	console.log('link =>',angular.toJson($scope.link.wsdl_endpoint));
    	$scope.link.project_id = $scope.json['id'];
    	$scope.link.user_id = 0;
    	console.log($scope.json['id']);
    	$http({
//            url: RestURL.baseURL + 'noun/save_wsdl_link/?link='+$scope.link.wsdlendpoint +'&project_id='+$scope.json['id'],
    		url: RestURL.baseURL + 'noun/save_wsdl_link/',
    		data: angular.toJson($scope.link),
            method: 'POST',
            headers: {
              'Accept': 'application/json'
            }
          })
            .success(function (response) {
            	alert('Success');
            	$scope.link.wsdl_endpoint = '';
            	console.log('Inside Success addWsdl Method')
            }).error(function (response){
            	console.log('Inside eRRROR addWsdl Method')
            	alert('Error');
            	$scope.link.wsdl_endpoint = '';
            });
    }
    
    $scope.restlink = {};
    $scope.addrestLink = function(link){
    	console.log('link for address Link ',angular.toJson(link));
    	$scope.restlink.project_id = $scope.json['id'];
    	$scope.restlink.user_id = 0;
    	console.log($scope.json['id']);
    	$http({
//            url: RestURL.baseURL + 'noun/save_wsdl_link/?link='+$scope.link.wsdlendpoint +'&project_id='+$scope.json['id'],
    		url: RestURL.baseURL + 'noun/save_rest_link/',
    		data: angular.toJson($scope.restlink),
            method: 'POST',
            headers: {
              'Accept': 'application/json'
            }
          })
            .success(function (response) {
            	alert('Success');
            	$scope.restlink.rest_endpoint = '';
            	console.log('Inside Success restlink.wsdl_endpoint Method')
            }).error(function (response){
            	console.log('Inside eRRROR restlink.wsdl_endpoint Method')
            	alert('Error');
            	$scope.restlink.rest_endpoint = '';
            });
    }
    
    
   
    
    $scope.import_other_noun = function(){
    	
    	$log.log("dbselected "+$scope.test.values); 	
    	$log.log("inside success of importing")
    	$http({
            url: RestURL.baseURL + 'couch/import_noun/'+$scope.json['id'],
            data: angular.toJson($scope.json['id']),
            method: 'GET',
            headers: {
              'Accept': 'application/json'
            }
          })
            .success(function (response) {
           // ajax for buckets 	
            	$log.log("success")
    	   $scope.isDisabled = true;
        	$log.log("inside success of get_all_couch_buckets")
        	$http.get(RestURL.baseURL + 'noun/get_all_couch_buckets/')
            .success(function (data) {
            	console.log(angular.toJson(data));
             $scope.other_nouns = data ;
             
              //ajax for views
              	/*log.log("success")
           	   $scope.isDisabled = true;
               	$log.log("inside success of get_all_couch_views")
               	$http.get(RestURL.baseURL + 'noun/get_all_couch_views/')
                   .success(function (data) {
                   	console.log(angular.toJson(data));
                   }).error(function(data){
                 	  $log.log(data);
                   })*/
             
            }).error(function (data) {
            $log.log(data);
            });
        }).error(function (response) {
        $log.log(response);
        });
       }
    
 //  WSDL nouns
    
 $scope.noun_wsdl = function(){
    	
    	$log.log("inside noun wsdl")
    	$http({
            url: RestURL.baseURL + 'wsdlNoun/get_all_wsdl_by_project_id/?project_id='+$scope.json['id'],
            data: angular.toJson($scope.json['id']),
            method: 'GET',
            headers: {
              'Accept': 'application/json'
            }
          }).success(function (data) {
          	$log.log("success")
          	console.log("data----->"+angular.toJson(data))
          	$scope.wsdlname = data;
          }).error(function (data) {
          $log.log(data);
          });
    	
       }
 
 $scope.get_class = function(SelectedWSDL){
	 $log.log("inside selected wsdl")
	 $log.log("SelectedWSDL------->"+angular.toJson(SelectedWSDL))
	 $scope.selectedWSDL = SelectedWSDL;
	 console.log(RestURL.baseURL + 'wsdlNoun/get_all_class_from_wsdl/?wsdlId='+SelectedWSDL.wsdlId+'&project_id='+SelectedWSDL.project_id)
	 $http({
         url: RestURL.baseURL + 'wsdlNoun/get_all_class_from_wsdl/?wsdlId='+SelectedWSDL.wsdlId+'&project_id='+SelectedWSDL.project_id,
         method: 'GET',
         headers: {
           'Accept': 'application/json'
         }
       }).success(function (data) {
       	$log.log("success")
       	console.log("data---class Name-->"+angular.toJson(data))
       	$scope.classname = data;
       }).error(function (data) {
       $log.log(data);
       });
	 
 }
    
    
 /* $scope.view_import_other_noun = function(){
    	
	  $log.log("inside success of importing")
  	$http.get(RestURL.baseURL + 'noun/get_all_predefined_nouns/')
      .success(function (data) {
       $scope.other_nouns = data ;
      }).error(function (data) {
      $log.log(data);
      });
      }
   */
       $scope.addwsdlNoun = function(wsdl_noun){
    	   console.log("selected wsdl noun",wsdl_noun)
    	   wsdl_noun.project_id=$scope.json['id']
           	$http({
                url: RestURL.baseURL + 'wsdlNoun/get_class_and_attribute/?id='+wsdl_noun.id,
                method: 'GET',
                headers: {
                  'Accept': 'application/json'
                }
           	}).success(function (data) {
               	$log.log("success")
               	console.log("data----->"+angular.toJson(data))
    	   $http({
               url: RestURL.baseURL + 'wsdlNoun/selected_other_noun/'+wsdl_noun.class_name+'/'+wsdl_noun.project_id,
               data: angular.toJson(data),
               method: 'POST',
               headers: {
                 'Accept': 'application/json'
               }
             })
            .success(function (response){
             $scope.nouns.push(response);
             
             })
            .error(function (response) {
            	console.error("error in attribure")
            });
           	}).error(function (response){
           	console.error("error in get class and attribute")
           		})
    	   
       }
       
       
       
       $scope.addDefaultNoun = function (selected_other_noun) {
        
        console.log("selected other noun",selected_other_noun);
        selected_other_noun.project_id = $scope.json['id']
        
        
        console.log("****dewfwesfsdfsscfsdcsdfsd**",selected_other_noun);
           $http({
                url: RestURL.baseURL + 'noun/selected_other_noun/',
                data: selected_other_noun,
                method: 'POST',
                headers: {
                  'Accept': 'application/json'
                }
              })
             .success(function (response) {
              $scope.nouns.push(response);
              alert("nouns added to project");
              })
             .error(function (response) {
               
             });
        
        
       }
       
    $scope.createNounNow = function () {

      if (!$scope.checkProjectIsExists($scope.noun)) {
        return;
      }
      $http({
        url: RestURL.baseURL + 'noun/create_noun/',
        data: angular.toJson($scope.noun),
        method: 'POST',
        headers: {
          'Accept': 'application/json'
        }
      })
        .success(function (response) {
         
          if (response != '') {
        	  $log.log('noun has been created successfully!');
            $scope.noun = {
              "technicalname": "",
              "activitiesusedin": "",
              "cacheenabled": "",
              "cacheable": true,
              "parents": [],
              "children": [],
              "nounattributes": [],
              "name": "",
              "description": "",
              "projectid": $scope.json['id'],
              "moduleid": $scope.json['default_module_id'],
              "label": "",
              "notes": ""
            };
            $scope.nouns.push(response);
            $scope.getAllActivities($scope, $scope.json['id']);
            instance.close('dismiss');
          }else{
        	  /**This code is used to show the message exist or not. it binds the message in html file that project_an.html*/
        	  console.log("$$ This name is already exist in the table. try to use another name please.") 
        	  $scope.myForm.name.$setValidity("ntValidName", false);       	  
          }
        })
        .error(function (response) {
        });
    };

    /**
     * update noun
     */
    $scope.updateNoun = function (noun) {
      $log.log("updating noun:", noun);
      $cookieStore.put('back', '/en-US/project/update/');
      ActivityInfo.setNoun(noun);
      ActivityInfo.setProjectId($scope.json['id']);
      $location.url('/en-US/noun/update');
    };

    $scope.deleteNounObj = {};


    $scope.setDeleteNounObj = function (nounObj) {
      $log.log('setting delete obj for noun');
      $scope.deleteNounObj = nounObj;
    };

    /**
     * delete noun
     */
    $scope.deleteNoun = function () {
      $log.log('delete noun has been called!', $scope.deleteNounObj);

      $http({
        url: RestURL.baseURL + 'noun/delete_noun/',
        data: angular.toJson($scope.deleteNounObj),
        method: 'POST',
        headers: {
          'Accept': 'application/json'
        }
      }).success(function (response) {

        var delIdx = -1;
        for (var idx in $scope.nouns) {
          if ($scope.nouns[idx].id == $scope.deleteNounObj.id) {
            delIdx = idx;
            break;
          }
        }

        if (delIdx > -1) {
          $scope.nouns.splice(delIdx, 1);
        }

        $scope.getAllActivities($scope, $scope.json['id']);
      }).error(function (response) {

      });

    };

    $scope.openProjectComponents = function () {
      if ($routeParams['action'] == 'update') {
        $(".project-components").slideToggle();
        $(".create-project-advanced").hide();
        $('html, body').animate({
          scrollTop: $(".project-components").offset().top
        }, 1000);
      }
    };

    $scope.accordion = function () {
      $("div.create-project-advanced").slideToggle();
      $("div.project-components").hide();
      $('html, body').animate({
        scrollTop: $("div.create-project-advanced").offset().top
      }, 1000);
    };

    /**
     * modal handler for activity and nouns
     */
    var instance;
    $scope.openModal = function (action, type, size, msg, title) {
      var modalDataObj = {
        action: action,
        type: type,
        createHandler: $scope.createHandler,
        deleteHandler: $scope.deleteHandler,
        message: msg,
        title: title,
        id: $scope.selectedProjectID
      };
      console.log("modalDataObj=======" + JSON.stringify(modalDataObj));
      instance = $modal.open({
        templateUrl: 'app/views/en-US/templates/modals/project/project_an.html',
        controller: 'ProjectModalCtrl',
        size: size,
        resolve: {
          modalDataObj: function () {
            return modalDataObj;
          }
        }
      });
      console.log("instance=======" + JSON.stringify(instance));
    };

    
    /**
     * create handler from modal as callback for noun and activity
     */
    $scope.createHandler = function (type, obj) {
      $log.log('create in project:', type, obj);
      if (type == 'noun') {
        $scope.noun.label = obj.label;
        $scope.noun.name = obj.name;
        $scope.noun.description = obj.description;
        $scope.createNounNow();
      } else if (type == 'activity') {
        $scope.activity.label = obj.label;
        $scope.activity.name = obj.name;
        $scope.activity.description = obj.description;
        $scope.activity.module_type = "default";
        $scope.createActivityNow();
      } else if (type == 'template') {
        $scope.projectTemplate.label = obj.label;
        $scope.projectTemplate.name = obj.name;
        $scope.projectTemplate.description = obj.description;
        $scope.projectTemplate.device = obj.device;
        $scope.createProjectTemplateNow();
      }
    };

    /**
     * delete handler from modal as callback for noun and activity
     */
    $scope.deleteHandler = function (type) {
      $log.log('delete in project:', type);
      if (type == 'noun') {
        $scope.deleteNoun();
      } else if (type == 'activity') {
        $scope.deleteActivity();
      } else if (type == 'template') {
        $scope.deleteProjectTemplate();
      }
    };

    /**
     * check project is exists
     */
    $scope.checkProjectIsExists = function (obj) {
      $log.debug("checking project is exists or not!");
      if (obj.projectid || obj.projectId) {
        $log.log("project id is valid!");
        return true;
      }
      $scope.openModal('alert', 'error', 'sm', 'Project is invalid, please create a project and then continue this action!', 'Invalid Project!');
      $log.log("project id is invalid!");
      return false;
    };

    $scope.additionalLanguageDropdown = function (selectedPrimaryLanguage) {
      $scope.getAllSecondaryLanguages(selectedPrimaryLanguage);
    };

    $scope.broswerLanguageDetection = function () {
      var lang = $window.navigator.language || $window.navigator.userLanguage;
      console.log("lanag" + lang);
      if (lang === 'en-US' || lang === 'en') {
        console.log("language is english");
        console.log("$scope.mainLanguages" + $scope.mainLanguages[0]);
        for (var i in $scope.mainLanguages) {
          if ($scope.mainLanguages[i].name == "English") {
            $scope.project.mainLanguage = $scope.mainLanguages[i];
            // $scope.project.mainLanguage = { id : 2 };
          }
        }
      }
      if (lang === 'de-de' || lang === 'de') {
        console.log("language is German - Germany");
        console.log("$scope.mainLanguages" + $scope.mainLanguages[0]);
        for (var i in $scope.mainLanguages) {
          if ($scope.mainLanguages[i].name == "German") {
            $scope.project.mainLanguage = $scope.mainLanguages[i];
            // $scope.project.mainLanguage = { id : 1 };
          }
        }
      }
      if (lang === 'fr' || lang === 'fr-fr') {
        console.log("language is French -France");
        console.log("$scope.mainLanguages" + $scope.mainLanguages[0]);
        for (var i in $scope.mainLanguages) {
          if ($scope.mainLanguages[i].name == "French") {
            $scope.project.mainLanguage = $scope.mainLanguages[i];
            // $scope.project.mainLanguage = { id : 3 };
          }
        }
      }
      if (lang === 'hy') {
        console.log("language is Armenian");
        console.log("$scope.mainLanguages" + $scope.mainLanguages[0]);
        for (var i in $scope.mainLanguages) {
          if ($scope.mainLanguages[i].name == "Armenian") {
            $scope.project.mainLanguage = $scope.mainLanguages[i];
            // $scope.project.mainLanguage = { id : 4 };
          }
        }
      }
      if (lang === 'it-it' || lang === 'it') {
        console.log("language is Italian - Italy");
        console.log("$scope.mainLanguages" + $scope.mainLanguages[0]);
        for (var i in $scope.mainLanguages) {
          if ($scope.mainLanguages[i].name == "Italian") {
            $scope.project.mainLanguage = $scope.mainLanguages[i];
            // $scope.project.mainLanguage = { id : 5 };
          }
        }
      }
      if (lang === 'ko') {
        console.log("language is Korean");
        console.log("$scope.mainLanguages" + $scope.mainLanguages[0]);
        for (var i in $scope.mainLanguages) {
          if ($scope.mainLanguages[i].name == "Korean") {
            $scope.project.mainLanguage = $scope.mainLanguages[i];
            // $scope.project.mainLanguage = { id : 7 };
          }
        }
      }
      if (lang === 'ja') {
        console.log("language is Japanese");
        console.log("$scope.mainLanguages" + $scope.mainLanguages[0]);
        for (var i in $scope.mainLanguages) {
          if ($scope.mainLanguages[i].name == "Japanese") {
            $scope.project.mainLanguage = $scope.mainLanguages[i];
            // $scope.project.mainLanguage = { id : 6 };
          }
        }
      }
      if (lang === 'pt-pt' || lang === 'pt') {
        console.log("language is Portuguese - portugal");
        console.log("$scope.mainLanguages" + $scope.mainLanguages[0]);
        for (var i in $scope.mainLanguages) {
          if ($scope.mainLanguages[i].name == "Portuguese") {
            $scope.project.mainLanguage = $scope.mainLanguages[i];
            //  $scope.project.mainLanguage = { id : 8 };
          }
        }
      }
      if (lang === 'ru') {
        console.log("language is Russian");
        console.log("$scope.mainLanguages" + $scope.mainLanguages[0]);
        for (var i in $scope.mainLanguages) {
          if ($scope.mainLanguages[i].name == "Russian") {
            $scope.project.mainLanguage = $scope.mainLanguages[i];
            //  $scope.project.mainLanguage = { id : 9 };
          }
        }
      }
      if (lang === 'es-es' || lang === 'es') {
        console.log("language is Spanish - Spain (Traditional)");
        console.log("$scope.mainLanguages" + $scope.mainLanguages[0]);
        for (var i in $scope.mainLanguages) {
          if ($scope.mainLanguages[i].name == "Spanish") {
            //  $scope.project.mainLanguage = { id : 10 };
          }
        }
      }
      if (lang === 'ta-ta' || lang === 'ta') {
        console.log("language is tamil");
        console.log("$scope.mainLanguages" + $scope.mainLanguages[0]);
        for (var i in $scope.mainLanguages) {
          if ($scope.mainLanguages[i].name == "Tamil") {
            $scope.project.mainLanguage = $scope.mainLanguages[i];
            // $scope.project.mainLanguage = { id : 11 };
          }
        }
      }
      if (lang === 'zh-cn' || lang === 'zh') {
        console.log("language is Chinese-China");
        console.log("$scope.mainLanguages" + $scope.mainLanguages[0]);
        for (var i in $scope.mainLanguages) {
          if ($scope.mainLanguages[i].name == "Chinese") {
            $scope.project.mainLanguage = $scope.mainLanguages[i];
            // $scope.project.mainLanguage = { id : 12 };
          }
        }
      }
      $scope.additionalLanguageDropdown($scope.project.mainLanguage.name);
    }


  }]);

