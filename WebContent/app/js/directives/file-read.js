app.directive("fileread", [function () {
  return {
    scope: {
      fileread: "="
    },
    link: function (scope, element, attributes) {
      element.bind("change", function (changeEvent) {
        var reader = new FileReader();
        reader.onload = function (loadEvent) {
          scope.$apply(function () {
            scope.fileread = loadEvent.target.result;
            //scope.fileread = scope.fileread.replace(/^data:image\/(png|jpg|jpeg);base64,/, "");
          });
        };
        reader.readAsDataURL(changeEvent.target.files[0]);
      });
    }
  }
}]);