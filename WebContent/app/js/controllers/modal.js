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

app.controller('ModalCtrl', ['$scope', '$modalInstance', 'data', 'authFactory', '$http', 'RestURL', '$timeout', '$window', '$log','$rootScope',
  function ($scope, $modalInstance, data, authFactory, $http, RestURL, $timeout, $window, $log, $rootScope) {

    $scope.modalData = {};
    console.log("data " + angular.toJson(data));
    var mname = [];
    mname= $rootScope.actobj1;
    
    $scope.modalData.mainLanguage = "";
    $scope.modalData.projectName = "";
    $scope.modalData.projectLabel = "";
    $scope.modalData.projectCompany = "";
    $scope.modalData.projectDescription = "";
    $scope.modalData.mainLanguages = [];
    $scope.mainLanguages = data.main;
    $scope.modalData.additionalLanguages = [];
    //$scope.modalData.additionalLanguages = data.additional;
    $scope.modalData.selectedadditionallanguages = [];

    $scope.additionalLanguageDropdown = function (selectedPrimaryLanguage) {
      $scope.getAllSecondaryLanguages(selectedPrimaryLanguage);
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
            if (selectedPrimaryLanguage != data[i]['ref_name']) {
              $scope.tempaddlanguages.push(addlng);
            }
          }
          //languageFactory.setData(data);
          $scope.modalData.additionalLanguages = $scope.tempaddlanguages;
          /*if ($routeParams['action'] == 'update') {
           fillInfo($scope);
           }*/

        }).error(function (data) {
        $log.log("lang data" + data);
      });
    };

    $scope.broswerLanguageDetection = function () {
      var lang = $window.navigator.language || $window.navigator.userLanguage;
      if (lang === 'en-US' || lang === 'en') {
        for (var i in $scope.mainLanguages) {
          if ($scope.mainLanguages[i].name == "English") {
            $scope.modalData.mainLanguage = $scope.mainLanguages[i];
          }
        }
      }
      if (lang === 'de-de' || lang === 'de') {
        for (var i in $scope.mainLanguages) {
          if ($scope.mainLanguages[i].name == "German") {
            $scope.modalData.mainLanguage = $scope.mainLanguages[i];
          }
        }
      }
      if (lang === 'fr' || lang === 'fr-fr') {
        for (var i in $scope.mainLanguages) {
          if ($scope.mainLanguages[i].name == "French") {
            $scope.modalData.mainLanguage = $scope.mainLanguages[i];
          }
        }
      }
      if (lang === 'hy') {
        for (var i in $scope.mainLanguages) {
          if ($scope.mainLanguages[i].name == "Armenian") {
            $scope.modalData.mainLanguage = $scope.mainLanguages[i];
          }
        }
      }
      if (lang === 'it-it' || lang === 'it') {
        for (var i in $scope.mainLanguages) {
          if ($scope.mainLanguages[i].name == "Italian") {
            $scope.modalData.mainLanguage = $scope.mainLanguages[i];
          }
        }
      }
      if (lang === 'ko') {
        for (var i in $scope.mainLanguages) {
          if ($scope.mainLanguages[i].name == "Korean") {
            $scope.modalData.mainLanguage = $scope.mainLanguages[i];
          }
        }
      }
      if (lang === 'ja') {
        for (var i in $scope.mainLanguages) {
          if ($scope.mainLanguages[i].name == "Japanese") {
            $scope.modalData.mainLanguage = $scope.mainLanguages[i];
          }
        }
      }
      if (lang === 'pt-pt' || lang === 'pt') {
        for (var i in $scope.mainLanguages) {
          if ($scope.mainLanguages[i].name == "Portuguese") {
            $scope.modalData.mainLanguage = $scope.mainLanguages[i];
          }
        }
      }
      if (lang === 'ru') {
        for (var i in $scope.mainLanguages) {
          if ($scope.mainLanguages[i].name == "Russian") {
            $scope.modalData.mainLanguage = $scope.mainLanguages[i];
          }
        }
      }
      if (lang === 'es-es' || lang === 'es') {
        for (var i in $scope.mainLanguages) {
          if ($scope.mainLanguages[i].name == "Spanish") {
            $scope.modalData.mainLanguage = $scope.mainLanguages[i];
          }
        }
      }
      if (lang === 'ta-ta' || lang === 'ta') {
        for (var i in $scope.mainLanguages) {
          if ($scope.mainLanguages[i].name == "Tamil") {
            $scope.modalData.mainLanguage = $scope.mainLanguages[i];
          }
        }
      }
      if (lang === 'zh-cn' || lang === 'zh') {
        for (var i in $scope.mainLanguages) {
          if ($scope.mainLanguages[i].name == "Chinese") {
            $scope.modalData.mainLanguage = $scope.mainLanguages[i];
          }
        }
      }
      $scope.additionalLanguageDropdown($scope.modalData.mainLanguage.name);
    };
    $scope.broswerLanguageDetection();

    $scope.modalData.multiselectsettings = {
      scrollableHeight: '200px',
      scrollable: true
    };

    //send when save message modal gets open
    $scope.modalData.action = data.action;
    $scope.modalData.message = data;
    $scope.modalData.jsonForProject = data.jsonForProject;
    $scope.modalData.projectId = data.projectId;

    //send when delete modal gets open
    $scope.modalData.id = data;

    $scope.ok = function (action) {
      $scope.modalData.action = action;
      $modalInstance.close($scope.modalData);
    };

    $scope.close = function () {
      $modalInstance.dismiss('cancel');
    };

    $scope.deleteHandler = function () {
      $modalInstance.close($scope.modalData.id);
    };

    $scope.finish = function () {
      $modalInstance.close($scope.modalData.id);
    };

    // $scope.closePleaseWaitModal = function () {
    //   $timeout(function () {
    //     $modalInstance.close($scope.modalData.id);
    //   }, 3000);
    // };

    $scope.toggleFinishBtn = true;
    $scope.modalData.generatemessage = "";
    $scope.generate_status = "";
    $scope.modalData.DesktopApp = false;
    $scope.modalData.gitCheck = false;
    
    $scope.requestDownloadDockerContainers = function () {
    	var currentUser = authFactory.getUser();
    	$('#btn-dockerDC').prop('disabled', true);
    	$http.post(RestURL.baseURL + 'docker/downloadrequests/start_generation?user_id=' + currentUser.userid,{headers: {'Accept': 'application/json'}})
    	.success(function (data) {
    		console.log(data);
    	}).error(function (data) {
    		$('#btn-dockerDC').prop('disabled', false);
    		console.log("generate error" + data);
    	});
    }
    
    $scope.updategeneratemodal = function () {
      var config = {headers: {'Accept': 'application/json'}};
      var currentUser = authFactory.getUser();
      $scope.modalData.generatemessage_title = "Generating Code , Please Wait........";
      var data_to_send = JSON.stringify({"user_id": currentUser.userid, "project_id": $scope.modalData.id, "user_name": currentUser.username});
      $http.post(RestURL.baseURL + 'job/start_generation', data_to_send, {headers: {'Accept': 'application/json'}})
        .success(function (the_job) {
          console.log("DATA " + JSON.stringify(the_job));
        $scope.modalData.generatemessage = "Generation Requested";
        (function tick() {
          var timer = $timeout(tick, 2000);
          $http.get(RestURL.baseURL + 'job/get_generation_status?parent_job_id=' + the_job.id, config)
            .success(function (data) {
            	console.log("DATA " + JSON.stringify(data));
              var gen_status = data.status;
              if(gen_status){
	              console.log("status: " + gen_status);
	              if (gen_status.match("gen_started")) {
	                $scope.modalData.generatemessage = "Generation started...";
	                $scope.modalData.spinner = true;
	              }
	              if (gen_status.match("gen_building")) {
	                $scope.modalData.generatemessage = "Building...";
	                $scope.modalData.spinner = true;
	              }
	              if (gen_status.match("gen_mobile")) {
	                $scope.modalData.generatemessage = "Building mobile apps...";
	                $scope.modalData.spinner = true;
	              }
	              if (gen_status.match("gen_github")) {
		                $scope.modalData.generatemessage = "Pushing code to GitHub...";
		                $scope.modalData.spinner = true;
		              }
	              if (gen_status.match("gen_error")) {
	                $scope.toggleFinishBtn = false;
	                $scope.modalData.generatemessage_title = "Something didn't work........";
	                $scope.modalData.generatemessage = "An Error has occurred while generating the application, please try again.. If the problem persist, please contact the administrator..";
	                $('#status').removeClass( "alert-info" ).addClass( "alert-danger" );
	                $scope.modalData.spinner = false;
	                $timeout.cancel(timer);
	                $http.get(RestURL.baseURL + 'gpconfigs/get_by_name?name=show_errors_in_web_app', config).success(function(response) {
	                    console.log('errors handling response' + JSON.stringify(response));
	                    if(response.value == 'true'){
	                    	$http.get(RestURL.baseURL + 'job/get_job_errors/' + the_job.id, config).success(function(errors) {
	                            console.log('errors ' + JSON.stringify(errors));
	                            $scope.generation_errors = errors;
	                            $scope.modalData.showerrors = true;
	                        });
	                    }
	                });
	                $scope.after_generation(the_job);
	              }
	              if (gen_status.match("gen_finished")) {
	            	  $scope.modalData.generationDone = true;
	            	  $scope.modalData.spinner =false;
	                $scope.toggleFinishBtn = false;
	                $scope.modalData.generatemessage_title = "Generation and deploy done!";
	                $scope.modalData.generatemessage = "Generation finished...";
	                angular.forEach(mname, function(value, key){
	                    console.log(key + ': ' + value);
	                $log.log("module name :"+value);
	                if(value != "GeppettoLogin Activity"){
	                	$log.log("GeppettoLogin Activity is absent")
	                }
	                else{
	                	if( $scope.modalData.generatemessage = "Generation finished..."){
	                		$scope.logincreds = true;
	                		$scope.modalData.spinner = false;
	                	}
	                }
	                });
	                $('#status').removeClass( "alert-info" ).addClass( "alert-success" );
	                $timeout.cancel(timer);
	                $scope.after_generation(the_job);
	              }
              }
            });
        })();

        }).error(function (data) {
        console.log("generate error" + data);
        $scope.toggleFinishBtn = false;
        $scope.modalData.spinner = false;
        $scope.modalData.generatemessage = "An Error has occurred while generating the application, please try again.. If the problem persist, please contact the administrator..";
      });

    };
    
    $scope.after_generation = function (the_job) {
    	var config = {headers: {'Accept': 'application/json'}};
   /* 	$http.get(RestURL.baseURL + 'job/get_job_by_status?parent_job_id=' + the_job.id + '&gen_status=gen_desktop_app', config).success(function(data) {
            console.log('desktop data ' + data);
            var app_desktop_status = data.status; 
            if(app_desktop_status.match("gen_desktop_app")){
            	$scope.modalData.DesktopApp = true;
            	$scope.modalData.AppDesktopLink = data.status_message;
            }
        });*/
    	
    	  $http.get(RestURL.baseURL + 'job/get_job_by_status?parent_job_id=' + the_job.id + '&gen_status=gen_desktop_app_awake', config).success(function(data) {
              var app_desktop_status = data.status; 
              if(app_desktop_status.match("gen_desktop_app_awake")){
            	$scope.modalData.desktopContainerComeUp = true;
              	$scope.modalData.DesktopApp  = false ;
              	$scope.modalData.desktopContainerComeUpMsg = data.status_message;
              }
          });
    	  
    	  $http.get(RestURL.baseURL + 'job/get_job_by_status?parent_job_id=' + the_job.id + '&gen_status=gen_desktop_app_fail', config).success(function(data) {
              var app_desktop_status = data.status; 
              if(app_desktop_status.match("gen_desktop_app_fail")){
            	  $scope.modalData.container_fail = true;
              	$scope.modalData.container_fail_message = data.status_message;
              }
          });
    	  
        $http.get(RestURL.baseURL + 'job/get_job_by_status?parent_job_id=' + the_job.id + '&gen_status=gen_github_finished', config).success(function(data) {
            console.log('desktop data ' + data);
            var app_desktop_status = data.status; 
            if(app_desktop_status.match("gen_github_finished")){
            	$scope.modalData.gitCheck = true;
            	$scope.modalData.gitUrl = data.status_message;
            }
        });
        
        $http.get(RestURL.baseURL + 'job/get_job_by_status?parent_job_id=' + the_job.id + '&gen_status=gen_ios_app_deploying', config).success(function(data) {
            console.log('gen_ionic_finished data ' + data);
            var gen_ionic_finished = data.status; 
            if(gen_ionic_finished.match("gen_ios_app_deploying")){
            	$scope.modalData.ionic_finished = true;
            	$scope.modalData.ionic_finished_message = "You can generate IOS mobile app after the successful " +
            			" \n installation of GeppettoConfigApp in your iPhone";//data.status_message;
            }
        });
 	  
      
    }

    $scope.projectModal = function () {
      var urlPart = "";
      var action = $scope.modalData.action;
      //var postSuccessMsg ="";
      var dataToPost;
      //var location_url = "";
      var proj_id = $scope.modalData.projectId;
      //$scope.modalData.message = "Please Wait....";
      if (action == "create") {
        urlPart = 'project/create_project/';
        $scope.modalData.postSuccessMsg = "Project Successfully created!";
        dataToPost = $scope.modalData.jsonForProject;
        //location_url = "/en-US/projects";

      }
      if (action == "update") {
        urlPart = 'project/update_project/';
        $scope.modalData.postSuccessMsg = "Project Successfully updated!";
        dataToPost = $scope.modalData.jsonForProject;
        //location_url = "/en-US/projects";
      }
      if (action == "add_module") {
        urlPart = 'module/add_module/';
        $scope.modalData.postSuccessMsg = "Module Successfully added!";
        var module = $scope.modalData.jsonForProject;
        dataToPost = {
          name: module.name,
          label: module.label,
          description: module.description,
          projectid: $scope.modalData.projectId,
          base_location: module.location_path,
          predefined_activity_id: module.id
        };
        //location_url = "/en-US/project/update";
      }
      $timeout(function () {
        $http({
          method: 'POST',
          url: RestURL.baseURL + urlPart,
          data: dataToPost,
          headers: {
            "content-type": "application/json",
            "Accept": "application/json"
          }
        }).success(function (data) {
          $modalInstance.close($scope.modalData);
          //$scope.modalData.message=postSuccessMsg;
          //$scope.toggleFinishBtn = false;
          //$location.url(location_url);
        }).error(function (data) {
          $log.log(data);
        });
      }, 3000);

      // alert("Action" + $scope.modalData.action);
      // if ($scope.modalData.action == "update") {
      //   $http.post(RestURL.baseURL + 'project/update_project/', jsonForProject)
      //     .success(function (data) {
      //       var msg = 'Project Successfully ' + $routeParams.action + 'd !';
      //       $scope.openSaveDialog('sm', msg);
      //       $scope.modalData.message = 'Project Successfully created!';
      //       $scope.toggleFinishBtn = false;
      //     }).error(function (data) {
      //     $log.log(data);
      //   });
      // }
      // if ($scope.modalData.action == "create") {
      //   $scope.modalData.message = "Please Wait....Project is under creation!";
      //   $timeout(function () {
      //     $http.post(RestURL.baseURL + 'project/create_project/', $scope.modalData.jsonForProject)
      //       .success(function (data) {
      //         $scope.modalData.message = 'Project Successfully created!';
      //         $scope.toggleFinishBtn = false;
      //         //$scope.openSaveDialog('sm',msg);
      //         //$scope.deleteCookies();
      //         $location.url("/en-US/projects");
      //       }).error(function (data) {
      //       $log.log("create project" + data);
      //     });
      //   }, 3000);
      // }
    }

  }]);