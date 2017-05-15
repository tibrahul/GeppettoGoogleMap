/**
 * Created by Henrikh on 12/1/15.
 */

app.controller('NounModalCtrl', ['$scope', '$http', '$compile', '$modalInstance', 'RestURL', 'ActivityInformation',
  function ($scope, $http, $compile, $modalInstance, RestURL, ActivityInfo) {

    var self = $scope;

    self.noun = {
      "technicalname": '',
      "activitiesusedin": '',
      "cacheenabled": '',
      "cacheable": true,
      "parents": [],
      "children": [],
      "nounattributes": [],
      "name": '',
      "label": '',
      "description": '',
      "projectid": ActivityInfo.projectId,
      "moduleid": '',
      "notes": ''
    };

    self.create = function () {
      if (self.noun_name && self.noun_label) {

        self.noun['name'] = self.noun_name;
        self.noun['label'] = self.noun_label;

        console.log(self.noun);

        $http({
          url: RestURL.baseURL + 'noun/create_noun/',
          data: JSON.stringify(self.noun),
          method: 'POST',
          headers: {
            "Accept": "application/json"
          }
        })
          .success(function (data) {
            $modalInstance.close();

            // refresh Axe
            

          })
          .error(function (error) {
          });
      }
    };

    self.addAttribute = function () {
      var table = angular.element('.noun_attributes_data table tbody');
      var id = table.find('tr').length;
      var html = ' \
          <tr> \
              <td> \
                  <input type="text" class="form-control" placeholder="Enter name" data-ng-model="noun_attribute_name_' + id + '" \
                         data-ng-change="changeAttrName(' + id + ')"> \
              </td> \
              <td> \
                  <input type="text" class="form-control" placeholder="Enter label" data-ng-model="noun_attribute_label_' + id + '" \
                         data-ng-change="changeAttrLabel(' + id + ')"> \
              </td> \
              <td> \
                  <select class="form-control" data-ng-model="noun_attribute_type_' + id + '" \
                          data-ng-change="changeAttrType(' + id + ')"> \
                      <option>Whole number</option> \
                      <option>Text</option> \
                      <option>Currency</option> \
                      <option>True/False</option> \
                  </select> \
              </td> \
          </tr> \
       ';

      /* add attribute to the table */
      table.append(html);

      $compile(table)($scope);

      /* add to object */
      var new_attribute = {
        createdate: "",
        createdby: "",
        datalength: "",
        description: "description",
        id: '',
        ispart_of_unique_key: "",
        label: self['noun_attribute_label_' + id],
        lastmodifiedby: "",
        lastmodifieddate: "",
        name: self['noun_attribute_name_' + id],
        notes: "",
        nounid: "",
        subtype: self['noun_attribute_type_' + id],
        subtypemodifiervalue: '',
        technicalname: "",
        type: ""
      };

      self.noun['nounattributes'].push(new_attribute);

      console.log('id: ', id);
      console.log('self.noun: ', self.noun);

    };

    self.changeAttrName = function (attr_id) {
      for (var i = 0; i < self.noun['nounattributes'].length; i++) {
        if (attr_id == self.noun['nounattributes'][i]['id']) {
          self.noun['nounattributes'][i]['name'] = self['noun_attribute_name_' + attr_id];
        }
      }
    };

    self.changeAttrLabel = function (attr_id) {
      for (var i = 0; i < self.noun['nounattributes'].length; i++) {
        if (attr_id == self.noun['nounattributes'][i]['id']) {
          self.noun['nounattributes'][i]['label'] = self['noun_attribute_label_' + attr_id];
        }
      }
    };

    self.changeAttrType = function (attr_id) {
      console.log(attr_id);
      for (var i = 0; i < self.noun['nounattributes'].length; i++) {
        if (attr_id == self.noun['nounattributes'][i]['id']) {
          self.noun['nounattributes'][i]['subtype'] = self['noun_attribute_type_' + attr_id];
        }
      }
    };

    self.close = function () {
      $modalInstance.dismiss('cancel');
    };
  }]);