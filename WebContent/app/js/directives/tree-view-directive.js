(function () {
  var tree = app.directive('tree', ['$log', '$timeout', '$compile', function ($log, $timeout, $compile) {
    return {
      restrict: 'E',
      templateUrl: 'app/views/en-US/directive/tree-directive.html',
      scope: {
        list: "=list",
        node: "=node"
      },
      compile: function (elem, attrs) {
        return function (scope, element, attributes) {

          /** Menu Tree Functions */
          scope.selectedItem = {};

          scope.options = {
            accept: function (sourceNodeScope, destNodesScope, destIndex) {
              return false;
              //return true;
            },
            beforeDrag: function (sourceNodeScope) {
              return true;
            }
          };

          scope.removeNode = function (node) {
            var nodeData = node.$modelValue;
            //console.log(angular.toJson(nodeData));
            node.remove();
          };

          scope.toggleNode = function (node) {
            node.toggle();
          };

          /** add child node */
          scope.newSubItem = function (node) {
            var nodeData = node.$modelValue;
            angular.element('#txtbox-name').focus();
            /*	if(nodeData.hasOwnProperty('menu_tree')) {
             nodeData.menu_tree.push({
             id : nodeData.id * 10 + nodeData.menu_tree.length,
             label : nodeData.label + '.' + (nodeData.menu_tree.length + 1),
             screen_id : 0,
             name : '',
             auth_id : 0,
             description : '',
             isChild : true,
             currentNode : false,
             menu_tree : []
             });
             }
             */
          };
          /** End */

          /** select node on click */
          scope.selectNode = function (node) {
            /** deselect all other nodes */
            scope.deSelectNodes(scope.list);
            node.currentNode = true;
            scope.node = node;
            $timeout(function () {
              scope.$apply();
            }, 200);
          };

          /** deselect all nodes */
          scope.deSelectNodes = function (node) {
            $.each(node, function (key, value) {
              value.currentNode = false;
              if (value.hasOwnProperty('screen_detail')) {
                if (value.screen_detail.length != 0) {
                  scope.deSelectNodes(value.screen_detail);
                }
              }
            });
          };
        }
      }
    };
  }]);
})();