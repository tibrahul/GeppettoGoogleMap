/**
 * created: Jan 4, 2017
 * @author Dhinakar Panner Selvam
 * Note:  This typevalidator is used to validate the Type selection in Noun designer page.To make required for all type(noun attributes)
 * 
 */



app.directive('typevalidator', ['$http', 'RestURL', '$q', '$timeout', 'RestrictedNames', function ($http, RestURL, $q, $timeout, RestrictedNames) {
  return {
    restrict: 'A',
    require: 'ngModel',
    link: function (scope, ele, attrs, ctrl) {
      function customValidator(ngModelValue) {
        if (ngModelValue === "---Select---"){
          ctrl.$setValidity('typevalidator', true);
        }
        return ngModelValue;
      }
      ctrl.$parsers.push(customValidator);
    }
  }
}]);