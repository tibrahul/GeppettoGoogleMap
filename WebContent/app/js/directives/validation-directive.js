/**
 * Modified: May 31, 2016
 * @author Kumaresan Perumal
 * Note:  Here when the user types, we check up the name for every letter. Now Dan decided to use 
 * that when the user hits submit button, The name is checked up in the database, that's why
 * i commented these tow lines.  " activity and noun"
 */


app.directive('validator', ['$http', 'RestURL', '$q', '$timeout', 'RestrictedNames', function ($http, RestURL, $q, $timeout, RestrictedNames) {
  return {
    restrict: 'A',
    require: 'ngModel',
    link: function (scope, ele, attrs, ctrl) {
      function customValidator(ngModelValue) {
        if (RestrictedNames.Names.length > 0) {
          for (var i = 0; i < RestrictedNames.Names.length; ++i) {
            if ((RestrictedNames.Names[i]) != null && (RestrictedNames.Names[i]).toLowerCase() == ngModelValue.toLowerCase()) {
              ctrl.$setValidity('restrictedNameValidator', false);
              break;
            } else {
              ctrl.$setValidity('restrictedNameValidator', true);
            }
          }
        }
                
        if (ngModelValue == "") {
          ctrl.$setValidity('uniqueNameValidator', true);
        }
        
        
        if(/^[-0-9_][-a-zA-Z0-9_]*$/.test(ngModelValue)){
        	   ctrl.$setValidity('startValidator', false);
        }else{
        	ctrl.$setValidity('startValidator', true);
        }
        
        if (/^\S*$/.test(ngModelValue)) {
          ctrl.$setValidity('whiteSpaceValidator', true);
        } else {
          ctrl.$setValidity('whiteSpaceValidator', false);
        }
        
        if (/^[A-Z].*$/.test(ngModelValue) != true && attrs.nameType != "entity") {
          ngModelValue = ngModelValue.charAt(0).toUpperCase() + ngModelValue.substring(1);
          ctrl.$setViewValue(ngModelValue);
          ctrl.$render();
        }
        if (attrs.nameType != "entity" && /^[-0-9_][-a-zA-Z0-9_]*$/.test(ngModelValue)) {
          ctrl.$setValidity('startAlphabetValidator', false);
        } else {
          ctrl.$setValidity('startAlphabetValidator', true);
        }
        if (attrs.nameType == "entity" && /^[a-zA-Z0-9]*$/.test(ngModelValue)) {
          ctrl.$setValidity('no_specialCharacterValidator', true);
        } else if (attrs.nameType == "entity") {
          ctrl.$setValidity('no_specialCharacterValidator', false);
        }

        if (/^[-a-zA-Z0-9_]*$/.test(ngModelValue)) {
          ctrl.$setValidity('specialCharacterValidator', true);
        } else {
          ctrl.$setValidity('specialCharacterValidator', false);
          return;
        }

        ctrl.$asyncValidators.validator = function (ngModelValue) {
          var defer = $q.defer();
          var nameBeforeEdit = attrs.nameBeforeEdit;
          var nameType = attrs.nameType;
          var projectID = attrs.projectId;
          var activityId = attrs.activityId;
          var urlPart = "";
          if (nameType == "project")
            urlPart = "project/search_for_exist_project/?project_name=" + ngModelValue;
        
         /* if (nameType == "activity")
            urlPart = "activity/get_all_activities_by_project_id/?project_id=" + projectID;*/
         /* if (nameType == "noun")
            urlPart = "noun/get_all_nouns_by_project_id/?project_id=" + projectID;*/
          if (nameType == "screen")
            urlPart = "android/get_all_screens_by_activity_id/?activity_id=" + activityId;
          if (nameType != undefined) {
            $timeout(function () {
              $http({
                method: 'GET',
                url: RestURL.baseURL + urlPart
              }).success(function (data, status, headers, cfg) {
                if (data.length > 0) {
                 // console.log("DATA" + data);
                  for (var i = 0; i < data.length; ++i) {
                    if (nameBeforeEdit != undefined) {
                      nameBeforeEdit = nameBeforeEdit.toLowerCase();
                    }
                    if ((data[i]['name']) != null && (data[i]['name']).toLowerCase() == ngModelValue.toLowerCase() && nameBeforeEdit != ngModelValue.toLowerCase()) {
                      ctrl.$setValidity('uniqueNameValidator', false);
                      defer.reject();
                      break;
                    } else {
                      ctrl.$setValidity('uniqueNameValidator', true);
                      defer.resolve();
                    }
                  }
                } else {
                  ctrl.$setValidity('uniqueNameValidator', true);
                  defer.resolve();
                }
                if (nameBeforeEdit != undefined) {
                  nameBeforeEdit = nameBeforeEdit.toLowerCase();
                }

                if (data && data.length == undefined) {
                  ctrl.$setValidity('uniqueNameValidator', true);
                  defer.resolve();
                }
                if (!data && data.length == undefined && nameBeforeEdit != ngModelValue.toLowerCase()) {
                  // Works when name is not unique
                  ctrl.$setValidity('uniqueNameValidator', false);
                  defer.reject();
                }
              }).error(function (data, status, headers, cfg) {
                ctrl.$setValidity('uniqueNameValidator', false);
                defer.reject();
              });
            }, 2000);
          }
          return defer.promise;
        };
        
        return ngModelValue;
      }

      ctrl.$parsers.push(customValidator);
    }
  }
}]);