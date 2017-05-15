/**
 * Since Geppetto.v90
 * Modified:  July 15 2015.
 * @author Pravin
 *
 * Note: Activity Controller has only the update action. creation of activity will be taken care on the project screen.
 *
 */

app.controller('ActivityCtrl',
  ['$log', '$scope','$routeParams', '$http', '$timeout', '$location', '$cookieStore', 'RestURL', 'ActivityInformation',
    '$modal','MicroServiceWizardFactory',
    function ($log, $scope,$routeParams, $http, $timeout, $location, $cookieStore, RestURL, ActivityInfo, $modal ,MicroServiceWizardFactory) {

      var self = $scope;

      self.activity = {
        phone_screens: [],
        tablet_screens: [],
        pc_screens: [],
        notes: null
      };
      self.markedRow = -1;
      self.secondary_nouns = [];
      self.primaryNouns = [];
      self.secondaryNouns = [];
      self.phoneDevices = [];
      self.tabletDevices = [];
      self.pcBasedDevices = [];
      self.enablecreateTabletbtn = true;
      self.enablecreatePhonebtn = true;
      self.enableTabletbtn = true;
      self.enablePhonebtn = true;

      self.activityInfo = {
        id: 0,
        primary_noun: {},
        secondary_nouns: []
      };

      /**
       * used to hold the current project informations
       */
      self.currentProject = {};

      /**
       * used to show the success message
       */
      self.successMessage = null;

      /**
       * holding editable noun object
       */
      self.editableNoun = null;

      /**
       * holding delete noun object
       */
      self.deleteNoun = null;

      self.activityData = {};

      /**
       * holding verbs
       */
      self.verbs = [];

      /*
       * holding Custom Verb
       */
      self.customVerb = [];
	  self.verbSection = {
			  value: "activity_verb"
	  };
      /**
       * initializing activity controller
       */
      self.init = function () {
        $log.debug("Activity Controller has been initialized!");

        $log.log('Activity Id :  ', ActivityInfo.activityId);
        $log.log('Project Id : ', ActivityInfo.projectId);

        self.activityData.name = ActivityInfo.activityName;
        self.activityData.proj_id = ActivityInfo.projectId;

        /**
         * initializing
         */
        self.getActivityToUpdate();
        self.getVerbsByActivity();
        self.getAllNounsByProject();
        self.loadAllDevices();
        self.getAllMicroserviceData();
        self.getProjectInfo();
      };

      /**
       * loading activity based on the activity id.
       */
      self.getActivityToUpdate = function () {

        $http.get(RestURL.baseURL + 'activity/search_for_update/?activity_id=' + ActivityInfo.activityId)
          .success(function (response) {
            self.allActivity = response;
            angular.copy(self.allActivity, self.activity);
          }).error(function (response) {
          $log.debug("unable to get the activity to update!");
        });
      };

      /**
       * loading verb based on the activity id.
       */
      self.getVerbsByActivity = function () {
    	  var activityData ={};
    	  $http.get(RestURL.baseURL + 'activity/search_for_update/?activity_id='+ ActivityInfo.activityId)
    	  .success(function(data){
    		  activityData=data;
    		  console.warn("success but need to find --> "+angular.toJson(activityData));
        	  if(angular.equals(activityData.module_type,"default_wsdl")){
        		  //need to call wsdl table for get all operation
        		  $http.get(RestURL.baseURL+'wsdlNoun/get_operations_by_wsdl_id/?wsdl_id='+activityData.wsdl_id)
        		  .success(function (response){
        			  self.wsdlVerbs = response;
        			  self.verbSection.value ="wsdl_custom_verb";
                      angular.copy(self.wsdlVerbs, self.customVerb);
        		  }).error(function (response){
        			  $log.debug("****************** ERROR");
        		  });
        	  }else{
        		  $http.get(RestURL.baseURL + 'verb/get_all_verbs_by_activity/?activity_id=' + ActivityInfo.activityId)
                  .success(function (response) {
                	  self.verbSection.value ="activity_verb";
                    self.allVerbs = response;
                    angular.copy(self.allVerbs, self.verbs);
                  }).error(function (response) {
                  $log.debug("unable to get the activity to update!");
                });
        	  }
    	  }).error(function(errordata){
    		  console.error("error occured!");
    	  });
    	
      };


      /**
       * loading all nouns based on the project
       */
      self.getAllNounsByProject = function () {

        $http.get(RestURL.baseURL + 'noun/get_all_nouns_by_project_id/?project_id=' + ActivityInfo.projectId)
          .success(function (response) {
            self.allNounProject = response;
            if (self.allNounProject != null) {
            	for (var i = 0; i < self.allNounProject.length; i++) {
					if(self.allNounProject[i].label==null || self.allNounProject[i].label==""){
						self.allNounProject[i].label = self.allNounProject[i].name;
					}
				}
              angular.copy(self.allNounProject, self.primaryNouns);
              angular.copy(self.allNounProject, self.secondaryNouns);
              console.log("self.secondaryNounsself.secondaryNouns  "+angular.toJson(self.secondaryNouns));
              //Update only when back from create noun screen
              var backUrl = $cookieStore.get('back');
              if (backUrl == "'/en-US/noun/create'") {
                self.updateCreatedNoun();
              }
              else if (backUrl == "'/en-US/axe/mobile/create/'" || backUrl == "'/en-US/axe/mobile/update/'"
                || backUrl == "/en-US/axe/tablet/create/" || backUrl == "/en-US/axe/tablet/update/") {
                //This for to set selected Object Of noun
                self.updatePrimaryNoun(self.activity.primary_noun);
              }
            } else {
              self.primaryNouns = [];
              self.secondaryNouns = [];
            }

            $timeout(function () {
              self.filterSecondaryNoun();
            }, 500);

          }).error(function (response) {
          $log.debug("Error: unable to loand the nouns!");
        });
      };

      self.filterSecondaryNoun = function () {
        // Remove primary noun from the secondary drop down options
        if (self.activity.primary_noun != null) {
          var len = self.secondaryNouns.length;
          for (var i = 0; i < len; i++) {
            if (self.activity.primary_noun.id === self.secondaryNouns[i].id) {
              self.secondaryNouns.splice(i, 1);
              break;
            }
          }
        }
        // Remove primary noun from the secondary selected options
        if (self.activity.primary_noun != null && self.activity.secondary_nouns != null) {
          var len = self.activity.secondary_nouns.length;
          for (var i = 0; i < len; i++) {
            if (self.activity.primary_noun.id === self.activity.secondary_nouns[i].id) {
              self.activity.secondary_nouns.splice(i, 1);
            }
          }
        }
      };

      /**
       * listening primary noun changes
       */
      self.pnoun = false;
      $scope.$watch('activity.primary_noun', function (newValue, oldValue) {
        if (self.pnoun) {
          if (newValue != null) {
            angular.copy(self.allNounProject, self.secondaryNouns);

            self.filterSecondaryNoun();
          }
        } else {
          self.pnoun = true;
        }
      });


      /**
       * Load All available designs
       */
      self.loadAllDevices = function () {
        $http.get(RestURL.baseURL + 'devicetypes/find_all_devices/')
          .success(function (response) {
            self.allscreens = response;
            var len = self.allscreens.length;
            for (var i = 0; i < len; i++) {
              if (response[i].client_device_type === 'phone') {
                self.phoneDevices.push(self.allscreens[i]);
              } else if (response[i].client_device_type == 'tablet') {
                self.tabletDevices.push(self.allscreens[i]);
              } else if (response[i].client_device_type == 'pc') {
                self.pcBasedDevices.push(self.allscreens[i]);
              }
            }
          }).error(function (response) {
          $log.debug("Error: unable to get all devices!");
        });
      };

      /**
       * retrieving project info
       */
      self.getProjectInfo = function () {
        if (ActivityInfo.projectId) {
          $http.get(RestURL.baseURL + 'project/search_for_update/?project_id=' + ActivityInfo.projectId)
            .success(function (data) {
              //$log.log('success: ', data);
              self.currentProject = data;
              if(self.currentProject.gpMicroService.length >0){
            	  MicroServiceWizardFactory.setgpMicroService(self.currentProject.gpMicroService);
            	  $scope.hidebutton=false;  
              }
            }).error(function (data) {
            $log.log('error:', data);
          });
        }
      };

      function openMessageDialog($modal, msg) {
    	  var modalInstance = $modal.open({
    	    animation: true,
    	    size: 'sm',
    	    backdrop: 'static',
    	    keyboard: false,
    	    templateUrl: 'app/views/en-US/templates/modals/axe/messagedialog.html',
    	    controller: 'ModalCtrl',
    	    resolve: {
    	      data: function () {
    	        return angular.copy(msg); // deep copy
    	      }
    	    }
    	  });
    	  return modalInstance;
    	}
      /**
       * this method will handle open for the modal
       */
      self.showMessage = function(){
    	  openMessageDialog($modal, 'Not Available at this time !')
	      .result.then(function (dataFromModal) {
	      }, function () {
      	});
      	return false;
      }
    
      self.openModal = function (type, size, action) {

        var modalDataObj = {};

        if (type == 'desktop') {
          modalDataObj.devices = self.activity.pc_screens;
          modalDataObj.screens = self.pcBasedDevices;
          modalDataObj.title = "Desktop Based View";
          modalDataObj.type = "Desktop";
        } else if (type == 'mobile') {
          modalDataObj.activity = self.activity;
          modalDataObj.devices = self.activity.phone_screens;
          modalDataObj.screens = self.phoneDevices;
          modalDataObj.title = "Mobile Based View";
          modalDataObj.type = "Mobile";
        } else if (type == 'tablet') {
          modalDataObj.devices = self.activity.tablet_screens;
          modalDataObj.screens = self.tabletDevices;
          modalDataObj.title = "Tablet Based View";
          modalDataObj.type = "Tablet";
        }

        modalDataObj.deleteCallback = self.deleteActivityDevice;
        modalDataObj.editCallback = self.editActivityDevice;
        modalDataObj.createScreenHandler = self.createDeviceScreen;
        modalDataObj.createWizardModal=$scope.createWizardModal;
        modalDataObj.editWizardModal=self.editWizardModal;
        modalDataObj.activity=self.activity;
        modalDataObj.action = action;

        var instance = $modal.open({
          templateUrl: 'app/views/en-US/templates/modals/activity/device.html',
          controller: 'ActivityModalCtrl',
          size: size,
          resolve: {
            type: function () {
              return type;
            },
            modalDataObj: function () {
              return modalDataObj;
            }
          }
        });

      };

      /**
       * callback for device screen creation
       */
      self.createDeviceScreen = function (screenType, device) {
        //$log.debug("screentype: ",screenType, ", device: ",device);
        if (angular.lowercase(screenType) == 'desktop') {
          self.cpcscreen();
        }

        if (angular.lowercase(screenType) == 'tablet') {
          self.selectTabletDevice = device;
          self.ctscreen();
        }

        if (angular.lowercase(screenType) == 'mobile') {
          self.selectPhoneDevice = device;
          self.cpscreen();
        }
      };

      /**
       * move to axe to create tablet screen
       */
      self.ctscreen = function () {

        if (self.selectTabletDevice) {
          $cookieStore.put('back', "'/en-US/activity/update'");
          self.activityInfo = self.activity;
          ActivityInfo.setActivtyInformantion(self.activityInfo);
          if (self.selectTabletDevice) {
            ActivityInfo.setDeviceTypes(self.selectTabletDevice);
          }
          $timeout(function () {
            $location.url('/en-US/axe/tablet/create/');
          }, 100);
        }
      };

      /**
       * move to axe to create mobile screen
       */
      self.cpscreen = function () {

        if (self.selectPhoneDevice) {
          $cookieStore.put('back', "'/en-US/activity/update'");
          self.activityInfo = self.activity;
          ActivityInfo.setActivtyInformantion(self.activityInfo);
          if (self.selectPhoneDevice) {
            ActivityInfo.setDeviceTypes(self.selectPhoneDevice);
          }

          $timeout(function () {
            $location.url('/en-US/axe/mobile/create/');
          }, 100);
        }
      };

      /**
       * create desktop screen
       */
      self.cpcscreen = function () {

        //if( self.selectPcDevice ){
        $cookieStore.put('back', "'/en-US/activity/update'");
        self.activityInfo = self.activity;
        ActivityInfo.setActivtyInformantion(self.activityInfo);
        /*if(self.selectPcDevice ){
         ActivityInfo.setDeviceTypes( self.selectPcDevice );
         }*/
        $timeout(function () {
          $location.url('/en-US/axe/desktop/create/');
        }, 100);
        //}
      };


      /**
       * delete callback for devices
       */
      self.deleteActivityDevice = function (screenType, device) {
        //$log.debug('device to delete:', device);
        if (device) {
          self.deleteScreenInput = device;
          self.deleteScreen();
        }
      };
      
      /**
       * delete callback for devices
       */
      self.deleteWizardScreenId = function (screenType, device) {
    	  //console.log("--->"+JSON.stringify(device));
    	  if (device) {
              self.deleteScreenInput = device;
              self.deleteWizardScreen();
            }
      };

      /**
       * delete screen
       */
      self.deleteScreen = function () {
        //$log.log("deleting a screen");
        if (!self.deleteScreenInput) {
          //$log.log("invalid screen to delete!");
          return;
        }

        if (!self.deleteScreenInput.children) {
          self.deleteScreenInput.children = {};
        }

        $http({
          url: RestURL.baseURL + 'android/delete_screen/',
          data: angular.toJson(self.deleteScreenInput),
          method: 'POST',
          headers: {
            'Accept': 'application/json'
          }
        })
          .success(function (response) {
            //$log.log("success:", response);

            if (self.deleteScreenInput) {

              if (self.deleteScreenInput.client_device_type === 'phone') {
                var idx = self.activity.phone_screens.indexOf(self.deleteScreenInput);
                self.activity.phone_screens.splice(idx, 1);

              } else if (self.deleteScreenInput.client_device_type == 'tablet') {
                var idx = self.activity.tablet_screens.indexOf(self.deleteScreenInput);
                self.activity.tablet_screens.splice(idx, 1);

              } else if (self.deleteScreenInput.client_device_type == 'pc') {
                var idx = self.activity.pc_screens.indexOf(self.deleteScreenInput);
                self.activity.pc_screens.splice(idx, 1);

              }
            }
            self.getActivityToUpdate();
            self.deleteScreenInput = null;
            self.showDeleteBtn = true;

          })
          .error(function (response) {
            $log.log("error:", response);
          });


      };

      /**
       * edit callback for devices
       */
      self.editActivityDevice = function (screenType, device) {
        //$log.debug("screentype: ",screenType, ", device: ",device);
        if (angular.lowercase(screenType) == 'desktop') {
          self.upcscreen(device);
        }

        if (angular.lowercase(screenType) == 'tablet') {
          self.updatesscreen = device;
          self.utscreen();
        }

        if (angular.lowercase(screenType) == 'mobile') {
          self.updatesphonescreen = device;
          self.upscreen();
        }
      };


      /**
       * Creating new noun
       */
      self.createNoun = function () {
        $cookieStore.put('back', "'/en-US/activity/update'");
        self.activityInfo = self.activity;
        ActivityInfo.setActivtyInformantion(self.activityInfo);
        $cookieStore.put('activity', JSON.stringify(self.activity));
        $location.url('/en-US/noun/create');
      };

      /**
       * cancel button handler on the activity screen
       */
      self.cancelActivity = function () {
        $location.url('/en-US/project/update/');
      }

      /**
       * update button handler for the activity screen
       */
      self.updateActivity = function () {
        //	self.activity.notes = self.richEditor.getHTML();
        $http({
          url: RestURL.baseURL + 'activity/update_activity/',
          data: angular.toJson(self.activity),
          method: 'POST',
          headers: {
            'Accept': 'application/json'
          }
        }).success(function (response) {
          $cookieStore.remove('back');
          $location.url('/en-US/project/update/');
        }).error(function (response) {
          $log.debug('unable to update the activity!', response);
        });
      };

      /**
       * delete button handler for the activity screen
       */
      self.deleteActivity = function () {
        $http({
          url: RestURL.baseURL + 'activity/delete_activity/',
          data: angular.toJson(self.activity),
          method: 'POST',
          headers: {
            'Accept': 'application/json'
          }
        }).success(function (response) {
          $location.url('/en-US/project/update/');
        }).error(function (response) {
          $log.debug('unable to delete the activity!', response);
        });
      };


      /**
       * This method will take care of picking noun once the new noun is created
       */
      self.updateCreatedNoun = function () {
        //var noun=ActivityInfo.noun;
        //self.updatePrimaryNoun(noun);
        ActivityInfo.setNoun({});
        $timeout(function () {
          $scope.$apply();
        }, 100);
      };

      /**
       * updating primary noun option on load either for update activity
       */
      self.updatePrimaryNoun = function (noun) {
        if (noun != undefined || !jQuery.isEmptyObject(noun)) {
          var len = self.primaryNouns.length;
          var idx = 0;
          for (var i = 0; i < len; i++) {
            if (noun.id == self.primaryNouns[i].id) {
              idx = i;
              break;
            }
          }
          self.activity.primary_noun = self.primaryNouns[idx];
        }
      };


      /**
       * update the existing tablet screen
       */
      self.utscreen = function () {
        if (self.updatesscreen) {
          $cookieStore.put('back', "'/en-US/activity/update'");
          self.activityInfo = self.activity;
          ActivityInfo.setActivtyInformantion(self.activityInfo);
          if (self.updatesscreen) {
            ActivityInfo.setDeviceTypes(self.updatesscreen);
          }
          $timeout(function () {
            $location.url('/en-US/axe/tablet/update/');
          }, 100);
        }
      };

      /**
       * update the existing phone screen
       */
      self.upscreen = function () {
        if (self.updatesphonescreen) {

          $cookieStore.put('back', "'/en-US/activity/update'");
          self.activityInfo = self.activity;
          ActivityInfo.setActivtyInformantion(self.activityInfo);
          if (self.updatesphonescreen) {
            ActivityInfo.setDeviceTypes(self.updatesphonescreen);
          }
          $timeout(function () {
            $location.url('/en-US/axe/mobile/update/');
          }, 100);
        }
      };

      /**
       * update desktop screen
       */
      self.upcscreen = function (screen) {

        if (screen) {

          $cookieStore.put('back', "'/en-US/activity/update'");
          self.activityInfo = self.activity;
          ActivityInfo.setActivtyInformantion(self.activityInfo);
          if (screen) {
            ActivityInfo.setDeviceTypes(screen);
          }
          $timeout(function () {
            $location.url('/en-US/axe/desktop/update/');
          }, 100);
        }
      };

      /**
       * Rashmi , adding wizard flow
      */
      $scope.createWizardModal = function(type){
      //open add project modal
        var modalInstance = $modal.open({
          backdrop: true,
          backdropClick: true,
          dialogFade: false,
          templateUrl: 'app/views/en-US/templates/modals/activity/createwizard.html',
          controller: 'WizardModalCtrl',
          resolve: {}
        });
        modalInstance.result.then(function (dataFromModal) {
      	$scope.modalData = dataFromModal;
      	$scope.name = self.modalData.name;
      	$scope.description = self.modalData.description;
      	$scope.activity_id= ActivityInfo.activityId;
      	
      	if (type == 'desktop') {
      		$scope.wizard_type = "pc";
        }else{
        	$scope.wizard_type = "phone";
        }
      	//form the post data
      	var wizarddata={
      			id: 0,
      			name:$scope.name,
      			description:$scope.description,
      			activity_id:$scope.activity_id,
      			screenIds:"",
      			wizard_type:$scope.wizard_type
      	};
      	//alert(angular.toJson(wizarddata));
      	$http({
            url: RestURL.baseURL + 'activity/create_wizard/',
            data: wizarddata,
            method: 'POST',
            headers: {
                'Accept': 'application/json'
            }
        })
        .success(function (response) {
        	var msg = "Wizard created successfully";
            $scope.openSaveDialog('sm', msg);
        })
        .error(function (response) {
          $log.log("error:", response);
        });
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
          });
        };
        
        /**
         * successfull message modal
        */
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
        
        /**
         * edit wizard modal
         */
        self.editWizardModal = function (type) {
            var modalDataObj = {};
            if (type == 'desktop') {
            	modalDataObj.wizard = self.activity.wizard_pc_screens;
            	modalDataObj.editCallback = self.editPCWizardScreenId;
            }else{
            	modalDataObj.wizard = self.activity.wizard_phone_screens;            	
            	modalDataObj.editCallback = self.editWizardScreenId;
            }
            modalDataObj.deleteCallback = self.deleteWizardScreenId;
            
            var instance = $modal.open({
              templateUrl: 'app/views/en-US/templates/modals/activity/editwizard.html',
              controller: 'editWizardModalCtrl',
              resolve: {
                modalDataObj: function () {
                  return modalDataObj;
                }
              }
            });

          };
          
          /**
           * delete wizard screen
           */
          self.deleteWizardScreen = function () {
            //$log.log("deleting a screen");

            $http({
              url: RestURL.baseURL + 'activity/deleteWizard_screen/',
              data: angular.toJson(self.deleteScreenInput),
              method: 'POST',
              headers: {
                'Accept': 'application/json'
              }
            })
              .success(function (response) {
            	  self.getActivityToUpdate();

                self.deleteScreenInput = null;
                self.showDeleteBtn = true;

              })
              .error(function (response) {
                $log.log("error:", response);
              });


          };

          /**
           * update desktop screen
           */
          self.editWizardScreenId = function (updateData,callback) {
        	 $http({
                  url: RestURL.baseURL + 'activity/updateWizard_screen/',
                  data: angular.toJson(updateData),
                  method: 'POST',
                  headers: {
                    'Accept': 'application/json'
                  }
                })
                  .success(function (response) {
                	  self.getActivityToUpdate();
                	  $scope.$watch('allActivity', function (newValue, oldValue, scope) {
                		  if (newValue != null) {
                			  callback(self.activity.wizard_phone_screens);
                		  }
                	});
                  })
                  .error(function (response) {
                    $log.log("error:", response);
                  });
          };
          
          /**
           * update desktop screen
           */
          self.editPCWizardScreenId = function (updateData,callback) {
        	 $http({
                  url: RestURL.baseURL + 'activity/updateWizard_screen/',
                  data: angular.toJson(updateData),
                  method: 'POST',
                  headers: {
                    'Accept': 'application/json'
                  }
                })
                  .success(function (response) {
                	  self.getActivityToUpdate();
                	  $scope.$watch('allActivity', function (newValue, oldValue, scope) {
                		  if (newValue != null) {
                			  callback(self.activity.wizard_pc_screens);
                		  }
                	});
                  })
                  .error(function (response) {
                    $log.log("error:", response);
                  });
          };
          
          
          $scope.hidebutton=true;
          $scope.hideEditbutton=true;
          //=======================================================================
      	$scope.microservice={};
      	$scope.createAddToWizardModal = function () {
        	  var modalInstance = $modal.open({
              backdrop: true,
              backdropClick: true,
              dialogFade: false,
              templateUrl: 'app/views/en-US/templates/modals/activity/createAddToWizard.html',
              controller: 'MicroServiceModalCtrl',
              resolve: {}
          });
        	modalInstance.result.then(function (DATA) {
        		$scope.activityId=[];
        		$scope.modalData= DATA;
          	//form the post data
        		$scope.activityId.push({
        			"activity_id" : ActivityInfo.activityId
        		   })
        		   console.log(JSON.stringify($scope.activityId));
        		var wizarddata={
          			id: $scope.modalData.id,
          			activities_json:JSON.stringify($scope.activityId)
          	};
        		
        		
        		//alert(angular.toJson(wizarddata));
          	$http({
                url: RestURL.baseURL + 'activity/search_for_update_microservice/',
                data: wizarddata,
                method: 'POST',
                headers: {
                    'Accept': 'application/json'
                }
            })
            .success(function (response) {
            	$scope.hideEditbutton=false;
            	var msg = "activity added successfully";
            	$scope.openSaveDialog('sm', msg);
//            	self.getAllMicroserviceData();
            	self.getProjectInfo();
            })
            .error(function (response) {
              $log.log("error:", response);
            });
            }, function () {
                $log.info('Modal dismissed at: ' + new Date());
              });
        
          }
          
          $scope.openEditMicroServiceWizard = function (){
        	  var modalDataObj = {};
        	  modalDataObj.UpdateCallBack=$scope.editMicroservicesWizardModal;
        	  modalDataObj.DeleteCallBack=$scope.openDeleteActivityFromWizard;
        	  var modalInstance = $modal.open({
                  backdrop: true,
                  backdropClick: true,
                  dialogFade: false,
                  templateUrl: 'app/views/en-US/templates/modals/activity/OpenEditMicroServiceWizard.html',
                  controller: 'MicroServiceEditModalCtrl',
                  resolve: {
                      modalDataObj: function () {
                        return modalDataObj;
                      }
                    }
              });
          }
          
          $scope.openDeleteActivityFromWizard=function(DATA){
          $http.get(RestURL.baseURL + 'activity/getData_for_update_microservice/?serviceId=' +DATA.id).success(function (response) {
        	  var modalDataObj = {};
        	  modalDataObj.wizard=response;
              modalDataObj.deleteCallback = self.deleteActivityFromWizard;
//              modalDataObj.editCallback = self.editMicroservicesWizardModal;
              var instance = $modal.open({
                  templateUrl: 'app/views/en-US/templates/modals/activity/editMicroServiceWizard.html',
                  controller: 'editMicroServiceWizardModalCtrl',
                  resolve: {
                    modalDataObj: function () {
                      return modalDataObj;
                    }
                  }
                });
              
    	  }).error(function (response) {
                  $log.log("error:", response);
                });

          }
         /* $scope.editMicroServiceWizard = function () {
              var modalDataObj = {};
              modalDataObj.wizard = self.wizardData;
              modalDataObj.deleteCallback = self.deleteActivityFromWizard;
              modalDataObj.editCallback = self.editMicroservicesWizardModal;
              var instance = $modal.open({
                templateUrl: 'app/views/en-US/templates/modals/activity/editMicroServiceWizard.html',
                controller: 'editMicroServiceWizardModalCtrl',
                resolve: {
                  modalDataObj: function () {
                    return modalDataObj;
                  }
                }
              });

            };*/
            
            $scope.launchMicroServiceWizard = function () {
            	$scope.modalInstance= $modal.open({
                         templateUrl: 'app/views/en-US/templates/modals/activity/LaunchWizard.html',
                         controller: 'ActivityCtrl'
                     });
            	MicroServiceWizardFactory.setModalData($scope.modalInstance);
              };
              
              
              $scope.close = function() {
            	  MicroServiceWizardFactory.modalData.close();
              };
              
            $scope.deleteActivityFromWizard = function(microServiceData){
            	console.log(microServiceData);
            	
        		var wizarddata={
        				id:microServiceData.id,
        				activities_json:'{"activity_id":'+microServiceData.activity_id+'}'
        		};
        		
        	 	$http({
                    url: RestURL.baseURL + 'activity/delete_Wizard_From_Activity/',
                    data: angular.toJson(wizarddata),
                    method: 'POST',
                    headers: {
                        'Accept': 'application/json'
                    }
                })
                .success(function (response) {
                	var msg = "Wizard deleted successfully";
                   	$scope.openSaveDialog('sm', msg);
                   	self.wizardData=response;
                   	self.getProjectInfo();
                })
            
            } 

//           $scope.getWizardInfo = function(){
//        	   var wizarddata={
//             			id: wizardToDelete.id,
//             			activities_json:""
//             	};
//
//        	   $http({
//                   url: RestURL.baseURL + 'activity/delete_Wizard_From_Activity/',
//                   data: angular.toJson(wizarddata),
//                   method: 'POST',
//                   headers: {
//                       'Accept': 'application/json'
//                   }
//               })
//               .success(function (response) {
//               	var msg = "Wixard deleted successfully";
//               	$scope.openSaveDialog('sm', msg);
//               	self.getAllMicroserviceData();
//               	self.getProjectInfo();
//               })
//         }
            
            self.getAllMicroserviceData = function(){
          	  $http.get(RestURL.baseURL + 'activity/get_all_micro_service/?projectId=' +ActivityInfo.projectId).success(function (response) {
                	if(response==null || response=="" ){
                		$scope.hideEditbutton=true;
                	}else{
                		$scope.hideEditbutton=false;
                	}
                	self.wizardData=response;
                	MicroServiceWizardFactory.setgpMicroService(response);
          	  })
                .error(function (response) {
                  $log.log("error:", response);
                });
            }
            

            /**
               * successfull message modal
              */
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

                $scope.createMicroservicesWizardModal = function () {
                    var modalInstance = $modal.open({
                      backdrop: true,
                      backdropClick: true,
                      dialogFade: false,
                      templateUrl: 'app/views/en-US/templates/modals/activity/createmicroserviceswizard.html',
                      controller: 'MicroserviceWizardModalCtrl',
                      resolve: {}
                    });
                    modalInstance.result.then(function (dataFromModal) {
                  	$scope.modalData = dataFromModal;
                  	$scope.name = $scope.modalData.microservice_name;
                  	$scope.description=$scope.modalData.description;
                  	$scope.notes=$scope.modalData.notes;
                  	//form the post data
                  	var wizarddata={
                  			project_id:self.currentProject.id,
                  			description:$scope.description,
                  			notes:$scope.notes,
                  			microservice_name:$scope.name,
                  			activities_json:""
                  	};
                  	//alert(angular.toJson(wizarddata));
                  	$http({
                        url: RestURL.baseURL + 'activity/create_microservice_wizard/',
                        data: wizarddata,
                        method: 'POST',
                        headers: {
                            'Accept': 'application/json'
                        }
                    })
                    .success(function (response) {
                    	var msg = "Wizard created successfully";
                    	$scope.openSaveDialog('sm', msg);
                    	self.getProjectInfo();
                    })
                    .error(function (response) {
                      $log.log("error:", response);
                    });
                    }, function () {
                        $log.info('Modal dismissed at: ' + new Date());
                      });
                    };
                      
                    $scope.editMicroservicesWizardModal = function (data) {

                        $http.get(RestURL.baseURL + 'activity/getData_for_microservice/?serviceId=' +data.id)
                          .success(function (response) {
                        	  MicroServiceWizardFactory.setactivityToUpdate(response);
                          	var modalInstance = $modal.open({
                                backdrop: true,
                                backdropClick: true,
                                dialogFade: false,
                                templateUrl: 'app/views/en-US/templates/modals/activity/editmicroserviceswizard.html',
                                controller: 'MicroserviceWizardModalCtrl',
                                resolve: {},
                                backdrop: 'static',
                                keyboard: false
                              });
                          	
                          	modalInstance.result.then(function (dataFromModal) {
                              	$scope.modalData = dataFromModal;
                              	$scope.name = $scope.modalData.microservice_name;
                              	$scope.description=$scope.modalData.description;
                              	$scope.notes=$scope.modalData.notes;
                              	$scope.id=$scope.modalData.id;
                              	//form the post data
                              	var wizarddata={
                              			id:$scope.id,
                              			description:$scope.description,
                              			notes:$scope.notes,
                              			microservice_name:$scope.name,
                              	};
                              	//alert(angular.toJson(wizarddata));
                              	$http({
                                    url: RestURL.baseURL + 'activity/update_microservice_wizard/',
                                    data: wizarddata,
                                    method: 'POST',
                                    headers: {
                                        'Accept': 'application/json'
                                    }
                                })
                                .success(function (response) {
                                	var msg = "Wizard updated successfully";
                                	$scope.openSaveDialog('sm', msg);
                                	self.getProjectInfo();
//                                	self.wizardData=dataFromModal;
//                                	$scope.editMicroServiceWizard();
                                })
                                .error(function (response) {
                                  $log.log("error:", response);
                                });
                                }, function () {
                                    $log.info('Modal dismissed at: ' + new Date());
                                  });

                          }).error(function (response) {
                          $log.debug("unable to get the activity to update!");
                        });

                          };
                  
                    
                    
                    /**
                     * successfull message modal
                    */
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

                
      /**
       * triggering init
       */
      self.init();
    }]);