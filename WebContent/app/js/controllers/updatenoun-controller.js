// Date Created :  March 13 2015
// @author Vijay
// Purpose of this controller to update the existing noun and nounattributes.
// since .75
/**
 * @deprecated deprecated version.85
 */

app.controller('UpdateNounController', ['$log', '$scope', '$http', '$timeout', 'RestURL', '$timeout', 'ActivityInformation',
    function ($log, $scope, $http, $timeout, RestURL, ActivityInfo) {
        var self = $scope;
        self.panelControl = true;
        self.isNone = true;
        self.parentOf = false;
        self.childOf = false;
        self.nounvalues = {
            nounattributes: []
        };
        $scope.$watch('nounData', function (newValue, oldValue) {
            if (self.nounData !== null && self.nounData !== undefined) {
                self.richEditor.setHTML(self.nounData.notes);
            }
            if (newValue != undefined) {
                self.nounvalues.id = newValue.id;
                self.nounvalues.name = newValue.name;
                self.nounvalues.description = newValue.description;
                self.nounvalues.label = newValue.label;

                for (var i = 0; i < newValue.nounattributes.length; i++) {
                    self.nounvalues.nounattributes.push(newValue.nounattributes [i]);
                }
            } else {
                self.nounvalues.id = 0;
                self.nounvalues.name = '';
                self.nounvalues.description = '';
                self.nounvalues.label = '';
            }
            if (oldValue != undefined) {
                for (var i = 0; i < oldValue.nounattributes.length; i++) {
                    var oldIdx = self.nounvalues.nounattributes.indexOf(oldValue.nounattributes [i]);
                    self.nounvalues.nounattributes.splice(oldIdx, 1);
                }
            }
        });
        self.deleteNounAttributes = function (nounAttributes) {
            var oldIdx = self.nounvalues.nounattributes.indexOf(nounAttributes);
            self.nounvalues.nounattributes.splice(oldIdx, 1);
        };
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
                nounid: self.nounData.nounid,
                technicalname: '',
                notes: '',
                createdate: '',
                createdby: '',
                lastmodifieddate: '',
                lastmodifiedby: ''
            };

            self.nounvalues.nounattributes.push(self.newattributes);
        };

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
                attributes.subtypemodifiervalue = 1/1/2015;
            }else if (attributes.subtype == 'Decimal') {
                attributes.subtypemodifiervalue = 9999.99;
            }else {
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
        self.updateNoun = function () {
            self.nounvalues.projectid = ActivityInfo.projectId;
            self.nounvalues.moduleid = 13;
            self.nounvalues.name = self.nounData.name;
            self.nounvalues.notes = self.richEditor.getHTML();
            $http({
                url: RestURL.baseURL + 'noun/update_noun/',
                data: self.nounvalues,
                method: 'POST',
                headers: {
                    'Accept': 'application/json'
                }
            })
                .success(function (response) {
                    $log.log('Noun has been updated sucessfully');
                })
                .error(function (response) {
                    $log.log('Error :');
                });
        };
        self.deleteNoun = function () {
            self.nounvalues.projectid = ActivityInfo.projectId;
            self.nounvalues.moduleid = 13;
            $http({
                url: RestURL.baseURL + 'noun/delete_noun/',
                data: self.nounvalues,
                method: 'POST',
                headers: {
                    'Accept': 'application/json'
                }
            })
                .success(function (response) {
                })
                .error(function (response) {
                });
        };
        self.init = function () {
            $log.log("Update noun controller initialized");
            self.rpanel = true;
            self.dpanel = false;
            $http.get(RestURL.baseURL + 'noun/get_all_nouns_by_project_id/?project_id=' + ActivityInfo.projectId)
                .success(function (response) {
                    $log.log("Success : noun values from response : ",
                        JSON.stringify(response));
                    self.allNounProject = response;
                    $log.log("Res : ", self.allNounProject);
                })
                .error(function (response) {
                    $log.log("Error : noun values from response : ",
                        JSON.stringify(response));
                });
            $http.get(RestURL.baseURL + 'noun/get_all_noun_type/')
                .success(function (response) {
                    $log.log("Success : noun Type from response : ",
                        JSON.stringify(response));
                    self.nounType = response;
                })
                .error(function (response) {
                    $log.log("Error : noun Type from response : ",
                        JSON.stringify(response));
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

            // Triggering Modal
            $('#btnStickUpSizeToggler').click(function () {
                $('#modalStickUpSmall').modal('show');
                var size = $('input[name=stickup_toggler]:checked').val()
                var modalElem = $('#myModal');
                if (size == "mini") {
                    $('#modalStickUpSmall').modal('show')
                } else {
                    $('#myModal').modal('show')
                    if (size == "default") {
                        modalElem.children('.modal-dialog').removeClass('modal-lg');
                    } else if (size == "full") {
                        modalElem.children('.modal-dialog').addClass('modal-lg');
                    }
                }
            });
        };
        self.init();

    }]);