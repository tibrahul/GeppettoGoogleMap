// Date Created: March 09 2015
// @author Vijay
// Purpose of this controller to create a new noun for the corresponding project. also user can copy their existing noun to this noun.
// since .75 
/**
 * @deprecated deprecated version of code from version .85
 */

app.controller('CreateNounController', ['$log', '$scope', '$http', '$timeout', '$location', '$cookieStore', 'RestURL', 'ActivityInformation',
  function ($log, $scope, $http, $timeout, $location, $cookieStore, RestURL, ActivityInfo) {
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
    self.init = function () {
      self.rpanel = true;
      self.dpanel = false;
      $http.get(RestURL.baseURL + 'noun/get_all_nouns_by_project_id/?project_id=' + ActivityInfo.projectId)
        .success(function (response) {
          self.allNounProject = response;
        })
        .error(function (response) {
          $log.log('Error : ');
        });
      $http.get(RestURL.baseURL + 'noun/get_all_noun_type/')
        .success(function (response) {
          self.nounType = response;
        })
        .error(function (response) {
          $log.log('Error : ');
        });

      $timeout(function () {
        /*Initializing text editor*/
        self.richEditor = new Quill('#_editor_rte', {
          theme: 'snow',
          modules: {
            'link-tooltip': true,
            'toolbar': {
              container: '#_toolbar_rte'
            }
          }
        });

      }, 1000);

    };
    self.init();
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

    $scope.$watch('nounData', function (newValue, oldValue) {
      if (self.nounData !== null && self.nounData !== undefined) {
        if (self.nounData.notes != null && self.nounData.notes != undefined) {
          self.richEditor.setHTML(self.nounData.notes);
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


    self.setModifier = function (attributes) {
      if (attributes.subtype == 'Text') {
        attributes.subtypemodifiervalue = 256;
      } else if (attributes.subtype == 'whole number') {
        attributes.subtypemodifiervalue = 9999;
      } else if (attributes.subtype == 'currency') {
        attributes.subtypemodifiervalue = '$9999.99';
      } else if (attributes.subtype == 'true/false') {
        attributes.subtypemodifiervalue = 'NA';
      } else if (attributes.subtype == 'A List') {
        attributes.subtypemodifiervalue = 'date';
      } else if (attributes.subtype == 'Date') {
        attributes.subtypemodifiervalue = '1/1/2015';
      } else if (attributes.subtype == 'Decimal') {
        attributes.subtypemodifiervalue = '9999.99';
      } else {
        attributes.subtypemodifiervalue = '';
      }

    };

    self.documentationPanel = function () {
      self.panelControl = false;
      if (self.dpanel) {
        self.dpanel = false;
      } else {
        self.dpanel = true;
        self.rpanel = false;
      }
    };

    self.requiredPanel = function () {
      self.panelControl = true;
      if (self.rpanel) {
        self.rpanel = false;
      } else {
        self.rpanel = true;
        self.dpanel = false;
      }
    };
    self.cancelNoun = function () {
      var lastUrl = $cookieStore.get('back');
      if (lastUrl != null) {
        if (lastUrl == "'/en-US/createactivity'") {
          $cookieStore.put('back', "'/en-US/createnoun'");
          $location.url('/en-US/createactivity');
        } else if (lastUrl == "'/en-US/updateactivity'") {
          $cookieStore.put('back', "'/en-US/createnoun'");
          $location.url('/en-US/updateactivity');

        }
      }
    };

    self.submitNoun = function () {
      self.nounvalues.projectid = ActivityInfo.projectId;
      /*Yet to fix the module ID*/
      self.nounvalues.moduleid = 13;

      self.nounvalues.label = self.nounvalues.name;
      self.nounvalues.notes = self.richEditor.getHTML();

      $http({
        url: RestURL.baseURL + 'noun/create_noun/',
        data: angular.toJson(self.nounvalues),
        method: 'POST',
        headers: {
          'Accept': 'application/json'
        }
      })
        .success(function (response) {
          ActivityInfo.setNoun(response);
          var lastUrl = $cookieStore.get('back');
          if (lastUrl != null) {
            if (lastUrl == "'/en-US/createactivity'") {
              $cookieStore.put('back', "'/en-US/createnoun'");
              $location.url('/en-US/createactivity');
            } else if (lastUrl == "'/en-US/updateactivity'") {
              $cookieStore.put('back', "'/en-US/createnoun'");
              $location.url('/en-US/updateactivity');
            }
          }
        })
        .error(function (response) {
        });
    }
  }]);