// Date Created :  March 09 2015
// @author Vijay
// Purpose of this directive to display label while loading and  on clicking the label, make the label as editable in the noun attributes.
// since .75

(function () {
  var editableData = app.directive('editableData', ['$log', '$timeout', function ($log, $timeout) {
    return {
      restrict: 'EA',
      scope: {
        input: '=inputData'
      },
      link: function ($scope, element, attrs) {
        $log.log('directive editable data is linked to the ui!');
        if (!$scope.input) {
          $scope.input = ' ';
        }
      },
      controller: ['$log', '$scope', function ($log, $scope) {
        $log.log('Editable Noun controller is initiated!');
        $log.log('Length : ', $scope.input.length);
      }],
      controllerAs: 'eData',
      templateUrl: 'app/views/en-US/directive/editable-data.html'
    };
  }]);
})();