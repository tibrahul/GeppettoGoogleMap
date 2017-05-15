app.controller('ProjectController', ['$scope', '$routeParams', '$log', '$location', '$http', '$timeout', '$cookieStore', '$window', 'ProjectData', 'languageFactory', 'RestURL', 'ActivityInformation', function ($scope, $routeParams, $log, $location, $http, $timeout, $cookieStore, $window, ProjectData, languageFactory, RestURL, ActivityInfo) {

  window.ProjectControllerScope = $scope;

  var link = '';

  $scope.getProjectsForUser = function () {
    $http.get(RestURL.baseURL + 'project/get_projects_for_user/')
      .success(function (data) {
        $scope.json = data;
      })
      .error(function (data) {
        $log.log("projects data" + data);
      });
  };

  $scope.json = ProjectData.project_data;

  $scope.selectedProjectID = selectedProjectID;
  $scope.selectedProjectInfo = selectedProjectInfo;

  $scope.selectProject = function (id) {
    if (self.selectedProjectID) {
      $('#' + self.selectedProjectID).removeClass("landing-project-selected");
    }
    $('#' + id).addClass("landing-project-selected");
    self.selectedProjectID = id;
    displayResponseForUser('ProjectController', $scope.getProjectNameByID(self.selectedProjectID));

    for (var i = 0; i < $scope.json.length; ++i) {
      if ($scope.json[i]['id'] == id) {
        ProjectData.setData($scope.json[i]);
      }
    }
    $location.url('/en-US/project/update/');
  };

  $(document).on("click", ".open-DeleteDialog", function () {
    var id = $(this).data('id');
    $(".modal-body #objectinput").val(id);
  });


  $scope.deleteProject = function () {
    var id = $scope.json['id'];
    if (id == undefined)
      id = $(".modal-body #objectinput").val();

    for (var i = 0; i < $scope.json.length; ++i) {
      if ($scope.json[i]['id'] == id) {
        //ProjectData.setData($scope.json[i]);
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
      "notes": $scope.json['notes'], //$scope.project_notes.getHTML(),
      "createdate": "",
      "createdby": "",
      "lastmodifieddate": "",
      "lastmodifiedby": "",
      "client_os_types": $scope.json['client_os_types'],
      "other_human_languages": $scope.json['other_human_languages'],
      "client_device_types": $scope.json['client_device_types'],
      "client_dev_languages": $scope.json['client_dev_languages'],
      "client_dev_frameworks": $scope.json['client_dev_frameworks'],
      "client_widget_frameworks": $scope.json['client_widget_frameworks'],
      "supported_browsers": $scope.json['supported_browsers'],
      "default_human_language": $scope.mainLanguage && $scope.mainLanguage['id'] ? $scope.mainLanguage['id'] : 2,
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
      "default_human_language": $scope.mainLanguage && $scope.mainLanguage['id'] ? $scope.mainLanguage['id'] : 2,
      "other_human_languages": ($scope.getSelectedOptionsForField('additional-language').length == 0) ? new Array($scope.defaultTechProperties['client_os_types'].toString()) : $scope.getSelectedOptionsForField('additional-language'),
      "entity": $scope.projectCompany,
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
      "multi_user_info": null
    };

    jsonForProject['id'] = id;
    jsonForProject['default_module_id'] = $scope.json['default_module_id'];
    jsonForProject['createdby'] = $scope.json['createdby'];

    $http.post(RestURL.baseURL + 'project/delete_project/', jsonForProject)
      .success(function (data) {
        $log.log('successfully ' + $routeParams.action + 'd project');
        $('body').removeClass('modal-open');
        $('.modal-backdrop').remove();
        $location.url("/en-US/project/list");

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

  $scope.gotoAdvanceOption = function () {
    $cookieStore.put('projectName', $scope.projectName);
    $cookieStore.put('projectLabel', $scope.projectLabel);
    $cookieStore.put('projectDescription', $scope.projectDescription);
    $cookieStore.put('projectCompany', $scope.projectCompany);
    if ($scope.mainLanguage != undefined)
      $cookieStore.put('mainLanguage', $scope.mainLanguage);
    else
      $cookieStore.put('mainLanguage', '');

    if ($scope.getSelectedOptionsForField('additional-language').length != 0) {
      var val = $scope.getSelectedOptionsForField('additional-language');
      $cookieStore.put('additionalLanguage', val);
    } else {
      $cookieStore.put('additionalLanguage', '');
    }
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
    $location.url('/en-US/project/advance/');

    //$window.location.reload(true);
  };


  $scope.submitCreateProject = function () {
    //alert($scope.projectNotes );
    var jsonForProject = {
      "id": 0,
      "name": $scope.projectName,
      "description": $scope.projectDescription,
      "label": $scope.projectLabel,
      "default_module_id": 0,
      "default_module_label": "",
      "notes": $scope.projectNotes, //$scope.project_notes.getHTML(),
      "createdate": "",
      "createdby": "",
      "lastmodifieddate": "",
      "lastmodifiedby": "",
      "client_os_types": ($scope.getSelectedOptionsForField('os-type').length == 0) ? new Array($scope.defaultTechProperties['client_os_types'].toString()) : $scope.getSelectedOptionsForField('os-type'),
      "client_device_types": ($scope.getSelectedOptionsForField('device-type').length == 0) ? ['12'] : $scope.getSelectedOptionsForField('device-type'), //new Array($scope.defaultTechProperties['client_device_types'].toString()) : $scope.getSelectedOptionsForField('device-type'),
      "client_dev_languages": ($scope.getSelectedOptionsForField('dev-languages').length == 0) ? new Array($scope.defaultTechProperties['client_dev_languages'].toString()) : $scope.getSelectedOptionsForField('dev-languages'),
      "client_dev_frameworks": ($scope.getSelectedOptionsForField('dev-frameworks').length == 0) ? new Array($scope.defaultTechProperties['client_dev_frameworks'].toString()) : $scope.getSelectedOptionsForField('dev-frameworks'),
      "client_widget_frameworks": [], //($scope.getSelectedOptionsForField('widget-frameworks').length == 0) ? $scope.defaultTechProperties['client_widget_frameworks'] : $scope.getSelectedOptionsForField('widget-frameworks'),
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
      "default_human_language": $scope.mainLanguage && $scope.mainLanguage['id'] ? $scope.mainLanguage['id'] : 2,
      "other_human_languages": ($scope.getSelectedOptionsForField('additional-language').length == 0) ? new Array($scope.defaultTechProperties['client_os_types'].toString()) : $scope.getSelectedOptionsForField('additional-language'),
      "entity": $scope.projectCompany,
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
      "multi_user_info": null
    };


    jsonForProject['id'] = 0;
    jsonForProject['default_module_id'] = 0;

    $http.post(RestURL.baseURL + 'project/create_project/', jsonForProject)
      .success(function (data) {
        $log.log('successfully ' + $routeParams.action + 'd project');
        $scope.deleteCookies();
        $location.url("/en-US/project/list");
        $window.location.reload(true);
      }).error(function (data) {
      $log.log("create project" + data);
    });
  };

  $scope.deleteCookies = function () {
    $cookieStore.remove("projectName");
    $cookieStore.remove("projectLabel");
    $cookieStore.remove("projectDescription");
    $cookieStore.remove("projectCompany");
    $cookieStore.remove("mainLanguage");
    $cookieStore.remove("additional-language");

  };

  $scope.cancelProject = function () {
    if ($routeParams['action'] == 'advance' || $routeParams['action'] == 'update') {
      $scope.deleteCookies();
      $location.url("/en-US/project/list");
      //$window.location.reload(true);
    }
  };


  $('#device-type').multiselect();
  $('#widget-frameworks').multiselect();
  //$('#additional-language').multiselect();
  $('#os-type').multiselect();
  $('#device-type').multiselect('uncheckAll');
  $('#widget-frameworks').multiselect('uncheckAll');
  //$('#additional-language').multiselect('uncheckAll');

  function emptyAllFields($scope) {
    $scope.projectName = "";
    $scope.projectLabel = "";
    $scope.projectCompany = "";
    $scope.projectDescription = "";
    $scope.mainLanguage = undefined;
    $scope.clientCodePattern = undefined;
    $scope.serverCodePattern = undefined;
    $scope.serverDevelopmentLanguage = undefined;
    $scope.serverDevelopmentFramework = undefined;
    $scope.serverRunTime = undefined;
    $scope.serverOs = undefined;
    $scope.serverDbms = undefined;
    $scope.serverDepEnviroment = undefined;
    $scope.appUiTemplate = undefined;
    $scope.uiNavigationStyle = undefined;
  }

  function emptyAdvanceFields($scope) {
    $scope.clientCodePattern = undefined;
    $scope.serverCodePattern = undefined;
    $scope.serverDevelopmentLanguage = undefined;
    $scope.serverDevelopmentFramework = undefined;
    $scope.serverRunTime = undefined;
    $scope.serverOs = undefined;
    $scope.serverDbms = undefined;
    $scope.serverDepEnviroment = undefined;
    $scope.appUiTemplate = undefined;
    $scope.uiNavigationStyle = undefined;
  }

  var mainLanguages = [
    {
      "val": "eng",
      "name": "English",
      "selected": "selected"
    },
    {
      "val": "arm",
      "name": "Armenian"
    },
    {
      "val": "ru",
      "name": "Russian"
    },
    {
      "val": "spa",
      "name": "Spanish"
    }
  ];

  var templateOptions = [
    {
      "val": "opt1",
      "name": "Option 1"
    },
    {
      "val": "opt2",
      "name": "Option 2"
    },
    {
      "val": "opt3",
      "name": "Option 3"
    },
    {
      "val": "opt4",
      "name": "Option 4"
    }
  ];

  $scope.techProperties = [];
  $scope.clientLanguage = [];
  $scope.serverLanguages = [];
  $scope.browsers = [];
  $scope.serverDevFramework = [];
  $scope.serverRunTimes = [];
  $scope.serverOS = [];
  $scope.serverDBMS = [];
  $scope.serverCodePatterns = [];
  $scope.serverDeploymentEnvironment = [];
  $scope.clientCodePatterns = [];
  $scope.clientDevFramework = [];
  $scope.desktopCssFrameworks = [];
  $scope.mobileCssFrameworks = [];
  $scope.clientWidgetFramework = [];
  $scope.clientOS = [];
  $scope.clientDeviceTypes = [];
  $scope.templateOptions = templateOptions;
  $scope.activities = {};
  $scope.nouns = {};
  $scope.languages = [];
  $scope.mainLanguages = [];
  $scope.additionalLanguages = [];
  $scope.defaultTechProperties = {};
  $scope.projectNotes = "";
  if ($scope.json['notes'] != '') {
    console.log("11111111111111" + $scope.projectNotes);
    $scope.projectNotes = $scope.json['notes'];
  }

  //$scope.mainLanguage = $scope.json['default_human_language'];
  $scope.appUiTemplate = $scope.json['app_ui_template'];
  $scope.uiNavigationStyle = $scope.json['ui_navigation_styles'];

  // putting all techproperties in multiselects

  $scope.putValues = function (selectID, data) {
    for (var i = 0; i < data.length; i++) {
      $("#" + selectID).append('<option value="' + data[i]["id"] + '">' + data[i]["name"] + '</option>');
    }
    $('select#' + selectID).multiselect();
    $('#' + selectID).multiselect('uncheckAll');
  };

  //getting all langauges
  $scope.getAllLanguages = function () {
    $http.get(RestURL.baseURL + 'language/get_all_languages/')
      .success(function (data) {
        for (var i = 0; data[i]; ++i) {
          var lng = {
            id: data[i]['id'],
            iso_id: data[i]['iso_id'],
            name: data[i]['ref_name']
          };
          $scope.languages.push(lng);
        }
        languageFactory.setData(data);

        $scope.mainLanguages = $scope.languages;
        $scope.additionalLanguages = $scope.languages;

        //console.log("ADDITINAL LANG"+angular.toJson($scope.additionalLanguages));
        $scope.putValues('additional-language', $scope.additionalLanguages);

        if ($routeParams['action'] == 'update') {
          fillInfo($scope);
        }

      }).error(function (data) {
      $log.log("lang data" + data);
    });
  };

  //getting all techProperties
  $scope.getAllTechProperties = function () {
    $http.get(RestURL.baseURL + 'techproperties/get_all_tech_properties/')
      .success(function (data) {
        $scope.techProperties = data;
        for (var i = 0; i < $scope.techProperties.length; ++i) {
          var obj = data[i];
          switch (obj["type"]) {
            case "GpServerLanguage":
              $scope.serverLanguages.push(obj);

              break;
            case "GpClientLanguage":
              $scope.clientLanguage.push(obj);

              break;
            case "GpBrowser":
              $scope.browsers.push(obj);

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
              $scope.clientOS.push(obj);

              break;
            case "GpServerDBMS":
              $scope.serverDBMS.push(obj);

              break;
            case "GpClientDevFramework":
              $scope.clientDevFramework.push(obj);

              break;
          }
        }

        $scope.putValues('os-type', $scope.clientOS);
        $scope.putValues('device-type', $scope.clientDeviceTypes);
        $scope.putValues('dev-languages', $scope.clientLanguage);
        //console.log("CLIENT LANG"+angular.toJson($scope.clientLanguage));
        $scope.putValues('dev-frameworks', $scope.clientDevFramework);
        //$scope.putValues('widget-frameworks', $scope.clientWidgetFramework); same problem
        $scope.putValues('supported-browsers', $scope.browsers);
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
            name: data[i]['client_device_type_label'],
            id: data[i]['id']
          };
          $scope.clientDeviceTypes.push(obj);
        }
        $scope.getAllLanguages();
        $scope.getAllTechProperties();
      })
      .error(function (data) {
        $log.log("device type" + data);
      });
  };

  $scope.getAllDeviceTypes();


  //selecting options for update screen
  $scope.setValueForSelect = function () {

    $scope.mainLanguage = {id: Number($scope.json['default_human_language'])};
    $scope.clientCodePattern = {id: Number($scope.json['client_code_pattern'])};
    $scope.serverCodePattern = {id: Number($scope.json['server_code_pattern'])};
    $scope.serverDevelopmentLanguage = {id: Number($scope.json['server_dev_lang'])};
    $scope.serverDevelopmentFramework = {id: Number($scope.json['server_dev_framework'])};
    $scope.serverRunTime = {id: Number($scope.json['server_run_time'])};
    $scope.desktopCssFramework = {id: Number($scope.json['desktop_css_framework'])};
    $scope.mobileCssFramework = {id: Number($scope.json['mobile_css_framework'])};
    $scope.serverOs = {id: Number($scope.json['server_os'])};
    $scope.serverDbms = {id: Number($scope.json['server_dbms'])};
    $scope.serverDepEnviroment = {id: Number($scope.json['server_deployment_environment'])};
  };


  $scope.setValuesForMultiselect = function (selectID, selectedOptions) {
    if (selectedOptions != undefined) {
      for (var i = 0; i < selectedOptions.length; ++i) {
        $('#' + selectID + ' option').each(function (index, node) {
          if ($(node).val() == selectedOptions[i]) {
            $(node).attr('selected', 'selected');
          }
          $("#" + selectID).multiselect("refresh");
        });
      }
    }
  };

  function fillInfo($scope) {
    //putting project values in fields
    $scope.projectName = $scope.json['name'];
    $scope.projectLabel = $scope.json['label'];
    $scope.projectCompany = $scope.json['entity'];
    $scope.projectDescription = $scope.json['description'];

    //calling method for Selects
    $scope.setValueForSelect();

    //calling method for multiSelects
    $scope.setValuesForMultiselect('os-type', $scope.json['client_os_types']);
    $scope.setValuesForMultiselect('additional-language', $scope.json['other_human_languages']);
    $scope.setValuesForMultiselect('device-type', $scope.json['client_device_types']);
    $scope.setValuesForMultiselect('dev-languages', $scope.json['client_dev_languages']);
    $scope.setValuesForMultiselect('dev-frameworks', $scope.json['client_dev_frameworks']);
    $scope.setValuesForMultiselect('widget-frameworks', $scope.json['client_widget_frameworks']);
    $scope.setValuesForMultiselect('supported-browsers', $scope.json['supported_browsers']);
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

  function getAllActivities($scope, projectID) {
    $http.get(RestURL.baseURL + 'activity/get_all_activities_by_project_id/?project_id=' + projectID)
      .success(function (data) {
        $scope.activities = data;
      }).error(function (data) {
      $log.log(data);
    });
  }

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

  var values = [];

  // This method is only for multiselect fields
  $scope.getSelectedOptionsForField = function (fieldID) {
    values = [];

    var options = $("#" + fieldID).multiselect("getChecked");

    for (var i = 0; i < options.length; i++) {
      values.push(options[i]["value"]);
    }

    return values;
  };

  $scope.updateActivity = function (activityId) {
    ActivityInfo.setActivityId(activityId);
    ActivityInfo.setProjectId($scope.json["id"]);

    $location.url('/en-US/activity/update');
  };

  $scope.createActivity = function () {
    ActivityInfo.setProjectId($scope.json["id"]);

    $location.url('/en-US/activity/create');
  };

  switch ($routeParams['action']) {
    case 'list':
      $cookieStore.put('back', '/en-US/project/list/');
      $scope.getProjectsForUser();
      emptyAllFields($scope);
      break;
    case 'advance':

      $cookieStore.put('back', '/en-US/project/advance/');
      $scope.projectName = $cookieStore.get('projectName');
      $scope.projectLabel = $cookieStore.get('projectLabel');
      $scope.projectDescription = $cookieStore.get('projectDescription');
      $scope.projectCompany = $cookieStore.get('projectCompany');

      $timeout(function () {
        $scope.setValuesForMultiselect('additional-language', $cookieStore.get('additionalLanguage'));
      }, 1000);

      if ($cookieStore.get('mainLanguage') != undefined)
        $scope.mainLanguage = {id: $cookieStore.get('mainLanguage')['id']};
      emptyAdvanceFields($scope);

      break;

    case 'update':

      $cookieStore.put('back', '/en-US/project/update/');
      getAllActivities($scope, $scope.json['id']);
      getAllNounsBasedOnProject($scope, $scope.json['id']);
      break;

    default :
      //TODO add error page
      $location.url('/en-US/login/');
  }

  /*	$('.submit').on('click', function() {
   var jsonForProject  = {
   "id": 0,
   "name": $scope.projectName,
   "description": $scope.projectDescription,
   "label": $scope.projectLabel,
   "default_module_id": 0,
   "default_module_label": "",
   "notes": "note for project ", //$scope.project_notes.getHTML(),
   "createdate": "",
   "createdby": "",
   "lastmodifieddate": "",
   "lastmodifiedby": "",
   "client_os_types": ($scope.getSelectedOptionsForField('os-type').length == 0) ? new Array($scope.defaultTechProperties['client_os_types'].toString()) : $scope.getSelectedOptionsForField('os-type'),
   "client_device_types": [], //($scope.getSelectedOptionsForField('device-type').length == 0) ? $scope.defaultTechProperties['client_device_types'] : $scope.getSelectedOptionsForField('device-type'),
   "client_dev_languages": ($scope.getSelectedOptionsForField('dev-languages').length == 0) ? new Array($scope.defaultTechProperties['client_dev_languages'].toString()) : $scope.getSelectedOptionsForField('dev-languages'),
   "client_dev_frameworks": ($scope.getSelectedOptionsForField('dev-frameworks').length == 0) ? new Array($scope.defaultTechProperties['client_dev_frameworks'].toString()) : $scope.getSelectedOptionsForField('dev-frameworks'),
   "client_widget_frameworks": [], //($scope.getSelectedOptionsForField('widget-frameworks').length == 0) ? $scope.defaultTechProperties['client_widget_frameworks'] : $scope.getSelectedOptionsForField('widget-frameworks'),

   "client_css_frameworks": ($scope.getSelectedOptionsForField('css-frameworks').length == 0) ? new Array($scope.defaultTechProperties['client_css_frameworks'].toString()) : $scope.getSelectedOptionsForField('css-frameworks'),
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
   "default_human_language": 0, // $scope.mainLanguage,
   "other_human_languages":[], //($scope.getSelectedOptionsForField('additional-language').length == 0) ? $scope.defaultTechProperties['other_human_languages'] : $scope.getSelectedOptionsForField('additional-language'),
   "entity": $scope.projectCompany,
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
   "multi_user_info": null
   };

   if ($routeParams['action']  == 'create') {
   jsonForProject['id'] = 0;
   jsonForProject['default_module_id'] = 0;

   $http.post(RestURL.baseURL + 'project/create_project/', jsonForProject)
   .success(function(data){
   $log.log('successfully ' + $routeParams.action + 'd project');
   $location.url('/en-US/landing/');
   }).error(function(data){
   alert(data);
   });
   } else if ($routeParams['action'] == 'update') {
   jsonForProject['id'] = $scope.json['id'];
   jsonForProject['default_module_id'] = $scope.json['default_module_id'];
   jsonForProject['createdby'] = $scope.json['createdby'];

   $http.post(RestURL.baseURL + 'project/update_project/', jsonForProject)
   .success(function(data){
   $log.log('successfully ' + $routeParams.action + 'd project');
   $location.url('/en-US/landing/');
   }).error(function(data){
   alert(data);
   });
   }
   });*/

  $scope.updateProject = function () {

    var jsonForProject = {
      "id": 0,
      "name": $scope.projectName,
      "description": $scope.projectDescription,
      "label": $scope.projectLabel,
      "default_module_id": 0,
      "default_module_label": "",
      "notes": $scope.projectNotes, //$scope.project_notes.getHTML(),
      "createdate": "",
      "createdby": "",
      "lastmodifieddate": "",
      "lastmodifiedby": "",
      "client_os_types": ($scope.getSelectedOptionsForField('os-type').length == 0) ? new Array($scope.defaultTechProperties['client_os_types'].toString()) : $scope.getSelectedOptionsForField('os-type'),
      "client_device_types": ($scope.getSelectedOptionsForField('device-type').length == 0) ? ['12'] : $scope.getSelectedOptionsForField('device-type'), //new Array($scope.defaultTechProperties['client_device_types'].toString()) : $scope.getSelectedOptionsForField('device-type'),
      "client_dev_languages": ($scope.getSelectedOptionsForField('dev-languages').length == 0) ? new Array($scope.defaultTechProperties['client_dev_languages'].toString()) : $scope.getSelectedOptionsForField('dev-languages'),
      "client_dev_frameworks": ($scope.getSelectedOptionsForField('dev-frameworks').length == 0) ? new Array($scope.defaultTechProperties['client_dev_frameworks'].toString()) : $scope.getSelectedOptionsForField('dev-frameworks'),
      "client_widget_frameworks": [], //($scope.getSelectedOptionsForField('widget-frameworks').length == 0) ? $scope.defaultTechProperties['client_widget_frameworks'] : $scope.getSelectedOptionsForField('widget-frameworks'),
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
      "default_human_language": $scope.mainLanguage && $scope.mainLanguage['id'] ? $scope.mainLanguage['id'] : 2,
      "other_human_languages": ($scope.getSelectedOptionsForField('additional-language').length == 0) ? new Array($scope.defaultTechProperties['client_os_types'].toString()) : $scope.getSelectedOptionsForField('additional-language'),
      "entity": $scope.projectCompany,
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
      "multi_user_info": null
    };

    jsonForProject['id'] = $scope.json['id'];
    jsonForProject['default_module_id'] = $scope.json['default_module_id'];
    jsonForProject['createdby'] = $scope.json['createdby'];

    $http.post(RestURL.baseURL + 'project/update_project/', jsonForProject)
      .success(function (data) {
        $log.log('successfully ' + $routeParams.action + 'd project');
        //$scope.cancelProject();
      }).error(function (data) {
      $log.log(data);
    });

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
    "description": ""
  };

  /**
   * creating new activity
   */
  $scope.createActivityNow = function () {
    $log.log("creating new activity!");
    $http({
      url: RestURL.baseURL + 'activity/create_activity/',
      data: angular.toJson($scope.activity),
      method: 'POST',
      headers: {
        'Accept': 'application/json'
      }
    })
      .success(function (response) {
        if (response != null) {
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

          $('#create-activity').modal('show');

        }
        $('#add-acti').modal('hide');
      })
      .error(function (response) {
        $('#add-acti').modal('hide');
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

        var delIdx = -1
        for (var idx in $scope.nouns) {
          if ($scope.activities[idx].id == $scope.deleteActivityObj.id) {
            delIdx = idx;
            break;
          }
        }

        if (delIdx > -1) {
          $scope.nouns.splice(delIdx, 1);
        }
        $('#delete-activity').modal('hide');

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
  $scope.createNounNow = function () {
    $http({
      url: RestURL.baseURL + 'noun/create_noun/',
      data: angular.toJson($scope.noun),
      method: 'POST',
      headers: {
        'Accept': 'application/json'
      }
    })
      .success(function (response) {
        $log.log('noun has been created successfully!');
        if (response != null) {

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

          $('#create-noun').modal('show');

        }
        $('#add-noun').modal('hide');

      })
      .error(function (response) {
        $('#add-noun').modal('hide');
      });
  };

  /**
   * update noun
   */
  $scope.updateNoun = function (noun) {
    $log.log("updating noun:", noun);
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
    })
      .success(function (response) {

        var delIdx = -1
        for (var idx in $scope.nouns) {
          if ($scope.nouns[idx].id == $scope.deleteNounObj.id) {
            delIdx = idx;
            break;
          }
        }

        if (delIdx > -1) {
          $scope.nouns.splice(delIdx, 1);
        }

        $('#delete-noun').modal('hide');
      })
      .error(function (response) {
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

}]);