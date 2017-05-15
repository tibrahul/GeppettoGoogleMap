app.directive('datePicker', function () {
  return {
    restrict: 'E',
    replace: true,
    template: '<div data-role="date" class="_Gp_element date input-group"> \
                  <input type="text" class="form-control"> \
                  <span class="input-group-addon"> \
                    <i class="fa fa-calendar"></i> \
                  </span> \
               </div>',
    compile: function () {
      return function ($scope, elem, attrs) {
        elem.datepicker();
      }
    }
  }
});