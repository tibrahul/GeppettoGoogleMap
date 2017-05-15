/**
 * Date Created: March 09 2015
 * @author Vijay
 * Purpose of this controller to create a new noun for the corresponding project. also user can copy their existing noun to this noun.
 * since .75
 *
 *Since Geppetto v.85
 * Modified Date: Jun 09, 2015
 * @author Pravin
 * noun controller with action parameter, action parameter decides the noun controller action either create or update.
 *
 * Note: I have written the actions only for 'create' need to include the actions for update noun.
 *
 * Since Geppetto v.90
 * Modified Date: July 16, 2015
 * @author Pravin
 *
 * Date Created: March 09 2016
 * @author Thamizhmani
 * I Created a Remove function to Remove a specific row of noun property grid.
 * since .75

 *
 * Note: Supports for both operations update and create.
 */

app.controller('NounCtrl', ['$log', '$scope', '$http', '$timeout', '$location',
  '$cookieStore', 'RestURL', 'ActivityInformation', '$routeParams','$modal',
  function ($log, $scope, $http, $timeout, $location, $cookieStore, RestURL, ActivityInfo, $routeParams,$modal) {

    var self = $scope;

    self.nounvalues = {
      technicalname: '',
      activitiesusedin: '',
      cacheenabled: '',
      cacheable: '',
      parents: [],
      children: [],
      nounattributes: []
    };

    self.panelControl = true;
    self.isNone = true;
    self.parentOf = false;
    self.childOf = false;

    /**
     * grid table options
     */
    self.gridOptions = {};

    /**
     * this mode is used to decide the noun controller has been initiated through which mode.
     * either 'create' or 'update'
     */
    self.mode = null;
    self.nounInfo = {};

    /**
     * initializing noun controller
     */
    self.init = function () {
      $log.log("noun controller has been initiated! action type is:", $routeParams.action);

      self.mode = $routeParams.action;

      self.getAllNounsBasedOnProject();
      self.getAllNounTypes();

      if (self.mode == "update") {
        self.nounvalues = ActivityInfo.noun;
        self.nounInfo.name = self.nounvalues.name;
        self.nounInfo.proj_id = ActivityInfo.projectId;
        //self.nounData.notes=self.nounvalues.notes;
      }

      if (self.mode == "create") {
        self.nounInfo.proj_id = ActivityInfo.projectId;
      }

    };

    /**
     * initialize grid
     */
    self.initializeGrid = function () {
//alert(angular.toJson(self.nounvalues.nounattributes));
      self.gridOptions = {
        data: self.nounvalues.nounattributes,
        enableSorting: true,
        enableFiltering: true,
        enableCellEditOnFocus: true,
        enableHorizontalScrollbar: 0,
        columnDefs: [{
          field: 'name', name: 'Name',
          cellTemplate: '<input type="text" ' +
          'class="form-control" id="propertyName" name="propertyName" placeholder="Enter name" ' +
          'ng-model="row.entity.name" validator name-type="property" project-id="{{nounInfo.proj_id}}"' +
          'data-ng-maxlength="50" data-ng-required="true">'
        }, {
          field: 'label', name: 'Label'
        }, {
          field: 'subtype', name: 'Type',
          cellTemplate: '<select class="hover ui-grid-filter-input"  typevalidator  name="propertyType"  id="propertyType" ' +
          'ng-model="row.entity.base_attr_type_id"' +
          'style="width: 100%; padding: 5px;"  data-ng-required="true" ' +
          'title="select type of noun attributes" autofocus="autofocus"' +
          'ng-options="nounType.id as nounType.subtype for nounType in grid.appScope.nounType"' +
          'ng-change="grid.appScope.setModifier(row.entity);">' +
          '<option value="">---Select---</option>' +
          '</select></td>'


          /*editType: 'dropdown',
           enableCellEdit: true,
           editableCellTemplate: 'ui-grid/dropdownEditor',
           editDropdownOptionsArray: self.nounType,
           editDropdownIdLabel: 'label',
           editDropdownValueLabel: 'label'*/
        }, {
          field: 'modifierName', name: 'Modifier'
        }, {
          field: 'modifierId', name: 'modifierId',visible:false
        }, {
        	field: 'modifierName', name: 'modifierName',visible:false
        }, {
          field: 'description', name: 'Description'
        }, {
          field: 'ispart_of_unique_key', name: 'Unique',
          enableSorting: false, enableFiltering: false,
          enableCellEdit: false,
          cellTemplate: '<label class="form-checkbox"  style="padding: 8px;"> <input type="checkbox" ng-model="row.entity.ispart_of_unique_key" ng-checked="row.entity.ispart_of_unique_key"> <span></span> </label>'
        }, {
            field: 'is_secondary_noun', name: 'Secondary noun',
            enableSorting: false, enableFiltering: false,
            enableCellEdit: false,
            cellTemplate: '<label class="form-checkbox"  style="padding: 8px;"> <input type="checkbox" ng-model="row.entity.is_secondary_noun" ng-checked="row.entity.is_secondary_noun"> <span></span> </label>'
          }
        , {
          /*do create remove option in noun rows*/
          field: 'remove', name: 'Remove',
          enableCellEdit: false,
          cellTemplate: '<button class="btn btn-danger btn-sm" ng-click="grid.appScope.remove(row)"><i class="fa fa-trash"></i></button>'
        }
        ]
      };

    };

    /**
     * registering listeners for radio buttons
     */
    self.registerListeners = function () {
      $timeout(function () {
        angular.element('[name="relationships"]').on('click', function () {
          var val = this.value;
          $log.debug('value', val);
          if (val == 'none') {
            self.isNone = true;
            self.parentOf = false;
            self.childOf = false;
            self.nounvalues.children = [];
            self.nounvalues.parents = [];
          }

          if (val == 'parent') {
            self.isNone = false;
            self.parentOf = true;
            self.childOf = false;
            self.nounvalues.children = [];
          }
          if (val == 'child') {
            self.isNone = false;
            self.parentOf = false;
            self.childOf = true;
            self.nounvalues.parents = [];
          }

          $scope.$apply();
        });


      }, 100);
    };

    /**
     * goto project
     */
    self.gotoProject = function () {
      $cookieStore.put('back', "'/en-US/activity/" + self.mode + "'");
      $location.url('/en-US/project/update');
    };

    /**
     * used to get all nouns based on project
     */
    self.getAllNounsBasedOnProject = function () {
      $http.get(RestURL.baseURL + 'noun/get_all_nouns_by_project_id/?project_id=' + ActivityInfo.projectId).success(function (response) {
        self.allNounProject = response;
      }).error(function (response) {
        $log.log('Error : ', response);
      });
    };

    /**
     * This method is used to get all noun types
     */
    self.stdType=[];
    self.getAllNounTypes = function () {
      $http.get(RestURL.baseURL + 'noun/get_all_noun_type/')
        .success(function (response) {
          self.nounType = response;
          // for using it in pop up list
          for(var i=0;i<self.nounType.length-2;i++){
        	 self.stdType.push(self.nounType[i]);
          }
         // alert(angular.toJson( self.stdType));
          self.initializeGrid();
          self.registerListeners();
        }).error(function (response) {
        $log.log('Error : ', response);
      });

    };

    self.iParent = false;
    $scope.$watch('noun.parents', function (newValue, oldValue) {
      if (!self.iParent) {
        self.iParent = true;
      } else {
        if (newValue != null) {
          self.parentOf = true;
          self.isNone = false;
        } else {
          self.parentOf = false;
          if (!self.childOf) {
            self.isNone = true;
          } else {
            self.isNone = false;
          }
        }
      }
      self.nounvalues.parents = [];
      if (newValue != null) {
        self.nounvalues.parents.push(newValue);
      }
      $timeout(function () {
        $scope.$apply();
      }, 100)
    });

    self.iChild = false;
    $scope.$watch('noun.children', function (newValue, oldValue) {
      //self.nounvalues.children=newValue;
      if (!self.iChild) {
        self.iChild = true;
      } else {
        if (newValue != null) {
          self.childOf = true;
          self.isNone = false;
        } else {
          self.childOf = false;
          if (!self.parentOf) {
            self.isNone = true;
          } else {
            self.isNone = false;
          }
        }
      }
      self.nounvalues.children = [];
      if (newValue != null) {
        self.nounvalues.children.push(newValue);
      }
      $timeout(function () {
        $scope.$apply();
      }, 100)
    });

    self.addNewRow = function () {
      self.newattributes = {
        name: 'Enter Name',
        label: 'Label',
        subtype: '',
        type: '',
        datalength: '',
        subtypemodifiervalue: '',
        description: 'description',
        ispart_of_unique_key: '',
        id: '',
        nounid: '',
        technicalname: '',
        notes: '',
        createdate: '',
        createdby: '',
        lastmodifieddate: '',
        lastmodifiedby: ''
      };
      self.nounvalues.nounattributes.push(self.newattributes);
    };

    self.remove = function (index) {

      console.log("inside the butten self.remove", index);
      var delIndex = self.nounvalues.nounattributes.indexOf(index.entity);
      console.log('delIndex No', delIndex)
      self.nounvalues.nounattributes.splice(delIndex, 1);

    };

    $scope.$watch('nounData', function (newValue, oldValue) {
      if (self.nounData !== null && self.nounData !== undefined) {
        if (self.nounData.notes != null && self.nounData.notes != undefined) {
          //self.richEditor.setHTML( self.nounData.notes );
        }
      }
      if (newValue != undefined) {
        for (var i = 0; i < newValue.nounattributes.length; i++) {
          self.nounvalues.nounattributes.push(newValue.nounattributes [i]);
        }
      }
      if (oldValue != undefined) {
        for (var i = 0; i < oldValue.nounattributes.length; i++) {
          var oldIdx = self.nounvalues.nounattributes.indexOf(oldValue.nounattributes [i]);
          self.nounvalues.nounattributes.splice(oldIdx, 1);
        }
      }
    });

    
   
    $scope.openNounListModal = function (attributes) {
    	var tempdata = {};
    	for (var i=0;i<self.allNounProject.length;i++){
    		if(self.allNounProject[i].id==self.nounvalues.id){
    			//alert(self.nounvalues.id);
    			var index = self.allNounProject.indexOf(self.allNounProject[i]);
    			if (index > -1) {
    				self.allNounProject.splice(index, 1);
    			}
    		}
    	}
    	if(attributes.base_attr_type_id==9){
    		var tempdata = {"nounlist":self.allNounProject,"stdlist":null};
    	}else if(attributes.base_attr_type_id==10){
    		var tempdata = {"nounlist":self.allNounProject,"stdlist":self.stdType};
    	}
        
        var modalInstance = $modal.open({
          animation: true,
          backdrop: 'static',
          keyboard: false,
          size: 'sm',
          templateUrl: 'app/views/en-US/templates/modals/noun/selectnoun.html',
          controller: 'ListModalCtrl',
          resolve: {
            data: function () {
              return angular.copy(tempdata); // deep copy
            }
          }
        });

        modalInstance.result.then(function (dataFromModal) {
          $scope.modalData = dataFromModal;
          console.log("MODAL DATA... "+angular.toJson($scope.modalData));
          if($scope.modalData.noun.selectedId!=""){
        	  attributes.modifierId = $scope.modalData.noun.selectedId.id;
        	  attributes.modifierName = $scope.modalData.noun.selectedId.name;
        	  attributes.subtypemodifiervalue = $scope.modalData.noun.selectedId.name;
          }else if($scope.modalData.std.selectedId!=""){
        	  attributes.modifierId = 0; 
        	  attributes.modifierName = $scope.modalData.std.selectedId.subtype;
        	  attributes.subtypemodifiervalue = $scope.modalData.std.selectedId.subtype;
          }
          
          console.log("attributes.modifierId"+angular.toJson(attributes.modifierId));
        }, function () {
          $log.info('Modal dismissed at: ' + new Date());
        });
      };

    self.setModifier = function (attributes) {
      for (i in self.nounType) {
    	if(attributes.base_attr_type_id==9 || attributes.base_attr_type_id==10){
    		//alert(attributes.base_attr_type_id)
    		$scope.openNounListModal(attributes);
    		break;
    	}  
        if (self.nounType[i].id === attributes.base_attr_type_id) {
          attributes.subtypemodifiervalue = self.nounType[i].sub_type_modifier;
          attributes.modifierName = self.nounType[i].sub_type_modifier;
          break;
        }
      }      

    };

    /**
     * cancel button handler for noun screen
     */
    self.cancelNoun = function () {
      var lastUrl = $cookieStore.get('back');
      $log.debug('last url:', lastUrl);
      if (lastUrl != null) {
        if (lastUrl == "'/en-US/activity/create'") {
          $cookieStore.put('back', "'/en-US/noun/create'");
          $location.url('/en-US/activity/create');
        } else if (lastUrl == "'/en-US/activity/update'") {
          $cookieStore.put('back', "'/en-US/noun/update'");
          $location.url('/en-US/activity/update');
        } else {
          $cookieStore.put('back', "'/en-US/noun/" + self.mode + "'");
          $location.url(lastUrl);
        }
      }
    };

    /**
     * create handler for new noun
     */
    self.submitNoun = function () {
      self.nounvalues.projectid = ActivityInfo.projectId;
      /*Yet to fix the module ID*/
      self.nounvalues.moduleid = 13;

      self.nounvalues.label = self.nounvalues.name;
      //self.nounvalues.notes = self.richEditor.getHTML();

      $http({
        url: RestURL.baseURL + 'noun/create_noun/',
        data: angular.toJson(self.nounvalues),
        method: 'POST',
        headers: {
          'Accept': 'application/json'
        }
      }).success(function (response) {
        ActivityInfo.setNoun(response);
        var lastUrl = $cookieStore.get('back');
        if (lastUrl != null) {
          if (lastUrl == "'/en-US/activity/create'") {
            $cookieStore.put('back', "'/en-US/noun/create'");
            $location.url('/en-US/activity/create');
          } else if (lastUrl == "'/en-US/activity/update'") {
            $cookieStore.put('back', "'/en-US/noun/create'");
            $location.url('/en-US/activity/update');
          }
        }
      }).error(function (response) {
      });
    };

    /**
     * update handler for existing noun
     */
    self.updateNoun = function () {
      //self.nounvalues.projectid = ActivityInfo.projectId;
      self.nounvalues.moduleid = 13;
      /*if(self.nounData.name){
       self.nounvalues.name = self.nounData.name;
       }
       self.nounvalues.notes = self.nounData.notes;*/
      $http({
        url: RestURL.baseURL + 'noun/update_noun/',
        data: self.nounvalues,
        method: 'POST',
        headers: {
          'Accept': 'application/json'
        }
      }).success(function (response) {
        $log.log('Noun has been updated sucessfully');
        $cookieStore.remove('back');
        $location.url('/en-US/project/update/');
      }).error(function (response) {
        $log.log('Error :');
      });
    };

    /**
     * Delete handler for existing noun
     */
    self.deleteNoun = function () {
      $log.log('deleting noun!');
      self.nounvalues.projectid = ActivityInfo.projectId;
      self.nounvalues.moduleid = 13;
      $http({
        url: RestURL.baseURL + 'noun/delete_noun/',
        data: self.nounvalues,
        method: 'POST',
        headers: {
          'Accept': 'application/json'
        }
      }).success(function (response) {
        $log.log('successfully deleted!');
        var lastUrl = $cookieStore.get('back');
        if (lastUrl != null) {
          $cookieStore.put('back', "'/en-US/noun/" + self.mode + "'");
          $location.url('/en-US/project/update');
        }
      }).error(function (response) {
      });
    };

    /**
     * triggering init
     */
    self.init();

  }]);