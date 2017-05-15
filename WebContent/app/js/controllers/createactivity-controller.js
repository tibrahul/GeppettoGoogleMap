// Date Created :  March 19 2015
// @author Vijay
// Purpose of this controller to update the existing noun and nounattributes.
// since .75
/**
 * Modified by Pravin
 * Since .85
 */

app.controller('CreateActivityController', ['$log', '$scope', '$http', '$timeout', '$location', '$cookieStore', 'ActivityInformation', 'RestURL',
    function ($log, $scope, $http, $timeout, $location, $cookieStore, ActivityInfo, RestURL) {
        var self = $scope;
        self.secondary_nouns = [];
        self.primaryNouns = [];
        self.secondaryNouns = [];
        self.phoneDevices = [];
        self.tabletDevices = [];
        self.pcBasedDevices = [];
        self.enablecreateTabletbtn = true;
        self.enablecreatePhonebtn = true;
        self.enableTabletbtn = true;
        self.enablePhonebtn = true;

        /**
         * used to hold the current project informations
         */
        self.currentProject = {};

        /**
         * used to show the success message
         */
        self.successMessage = null;

        /**
         * holding editable noun object
         */
        self.editableNoun = null;

        /**
         * holding delete noun object
         */
        self.deleteNoun = null;


        self.init = function () {
            self.showupdatebtn = true;
            self.showCreate = true;
            self.disablecreate = false;
            self.accordian = 1;
            self.saccordian = 2;
            self.enableTabletbtn = true;
            self.enablePhonebtn = true;
            var backUrl = $cookieStore.get('back');
            $log.log('back', backUrl);
            if (backUrl == "'/en-US/noun/create'") {
                var act = $cookieStore.get('activity')
                if (act != undefined) {
                    self.activity = angular.fromJson(act);
                    if (self.activity.id != undefined && self.activity.id != null && self.activity.id != 0) {
                        self.showupdatebtn = false;
                        self.showCreate = false;
                        self.disablecreate = true;

                    } else {
                        self.showCreate = true;
                        self.disablecreate = false;
                        self.showupdatebtn = true;
                    }
                }

            } else if (backUrl == "'/en-US/axe/mobile/create/'" || backUrl == "'/en-US/axe/mobile/update/'"
                || backUrl == "/en-US/axe/tablet/create/" || backUrl == "/en-US/axe/tablet/update/") {

                self.updatedActivity = ActivityInfo.activity;
                self.createdScreen = ActivityInfo.deviceTypes;


                if (self.updatedActivity != undefined && self.updatedActivity != null) {
                    if (self.updatedActivity.id != undefined && self.updatedActivity.id != null && self.updatedActivity != 0) {
                        self.activity = angular.fromJson(self.updatedActivity);
                        self.showupdatebtn = false;
                        self.showCreate = false;
                        self.disablecreate = true;
                    } else {
                        self.showCreate = true;
                        self.disablecreate = false;
                        self.showupdatebtn = true;
                    }
                }
                if (self.createdScreen != null && self.createdScreen != undefined) {

                    $http.get(RestURL.baseURL + 'activity/search_for_update/?activity_id=' + self.updatedActivity.id)
                        .success(function (response) {
                            self.activity.tablet_screens = response.tablet_screens;
                            self.activity.phone_screens = response.phone_screens;
                            $timeout(function () {
                                $scope.$apply();
                            }, 100)
                        })
                        .error(function (response) {
                        });

                }
            } else {
                $log.log('no history: execute the regular activities');
            }
            $http.get(RestURL.baseURL + 'noun/get_all_nouns_by_project_id/?project_id=' + ActivityInfo.projectId)
                .success(function (response) {
                    self.allNounProject = response;
                    self.getAllNouns();

                    //Update only when back from create noun screen
                    var backUrl = $cookieStore.get('back');
                    if (backUrl == "'/en-US/createnoun'") {
                        self.updateCeratedNoun();
                    }
                    else if (backUrl == "'/en-US/axe/create/'" || backUrl == "'/en-US/axe/update/'"
                        || backUrl == "/en-US/axe/create/" || backUrl == "/en-US/axe/update/") {
                        //This for to set selected Object Of noun
                        self.updatePrimaryNoun(self.activity.primary_noun);
                    }
                    //Remove Primary noun from secondary && Remove secondary from primary
                    self.removeRepeatedNouns();
                })
                .error(function (response) {
                });
            $http.get(RestURL.baseURL + 'devicetypes/find_all_devices/')
                .success(function (response) {
                    self.allscreens = response;
                    var len = self.allscreens.length;
                    for (var i = 0; i < len; i++) {
                        if (response[i].client_device_type === 'phone') {
                            self.phoneDevices.push(self.allscreens[i]);
                        } else if (response[i].client_device_type == 'tablet') {
                            self.tabletDevices.push(self.allscreens[i]);
                        } else if (response[i].client_device_type == 'pc') {
                            self.pcBasedDevices.push(self.allscreens[i]);
                        } else {
                        }
                    }
                })
                .error(function (response) {
                });
            /*	$timeout( function(){
             self.richEditor=new Quill( '#_editor_rte', {
             theme: 'snow',
             modules: {
             'link-tooltip': true,
             'toolbar': {
             container: '#_toolbar_rte'
             }
             }
             });

             },1000);*/

            self.getProjectInfo();

        };


        /**
         * goto project
         */
        self.gotoProject = function () {
            $cookieStore.put('back', "'/en-US/activity/create'");
            $location.url('/en-US/project/update');
        };


        /**
         * retrieving project info
         */
        self.getProjectInfo = function () {
            if (ActivityInfo.projectId) {
                $http.get(RestURL.baseURL + 'project/search_for_update/?project_id=' + ActivityInfo.projectId)
                    .success(function (data) {
                        $log.log('success: ', data);
                        self.currentProject = data;
                    }).error(function (data) {
                        $log.log('error:', data);
                    });
            }
        };

        /**
         * setting editable secondary noun
         */
        self.setEditableSecondaryNoun = function (snoun) {
            $log.log("edit secondary noun has been called!", snoun);
            self.editableNoun = snoun;
        };

        /**
         * Setting deletable secondary noun
         */
        self.setDeleteSecondaryNoun = function (snoun) {
            $log.log("delete secondary noun has been called!", snoun);
            self.deleteNoun = snoun;
        }


        /**
         * updating secondary noun
         */
        self.updateSecondaryNoun = function () {
            var url = RestURL.baseURL + '/noun/update_noun/';
            $http({
                method: "POST",
                url: url,
                data: self.editableNoun,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).success(function (data) {
                $log.log("update is success", data);

                self.successMessage = "Secondary Noun has been updated successfully!";
                self.toggleSuccessMessage('show');
                $('#edit-sec-noun').modal('hide');
            }).error(function (data) {
                $log.log("error:", data);
            });
        };

        /**
         * deleting secondary noun
         */
        self.deleteSecondaryNoun = function () {
            var url = RestURL.baseURL + '/noun/delete_noun/';
            $http({
                method: "POST",
                url: url,
                data: self.deleteNoun,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).success(function (data) {
                $log.log("update is success", data);

                self.successMessage = "Secondary Noun has been deleted successfully!";
                self.toggleSuccessMessage('show');
                $('#delete-noun').modal('hide');

                /**
                 * deleting the noun locally
                 */
                var idx = self.activity.secondary_nouns.indexOf(self.deleteNoun);
                if (idx > -1) {
                    self.activity.secondary_nouns.splice(idx, 1);
                }

            }).error(function (data) {
                $log.log("error:", data);
            });
        };


        self.activity = {
            id: 0,
            name: '',
            activity_types: [],
            projectid: ActivityInfo.projectId,
            moduleid: 13,
            primary_noun: {},
            secondary_nouns: [],
            tablet_screens: [],
            phone_screens: [],
            pc_screens: [],
            notes: ''

        };
        self.activityInfo = {
            id: 0,
            primary_noun: {},
            secondary_nouns: [],
        };
        self.getAllNouns = function () {
            if (self.allNounProject != null) {
                angular.copy(self.allNounProject, self.secondaryNouns);
                angular.copy(self.allNounProject, self.primaryNouns);
            } else {
                self.secondaryNouns = [];
                self.primaryNouns = [];
            }

        };
        self.init();

        self.testSelected = function (index, selectedScreen) {
            self.markedRow = index;
            self.enableTabletbtn = false;
            self.updatesscreen = selectedScreen;
            $timeout(function () {
                $scope.$apply();
            }, 100)

        }
        self.upSelected = function (index, selectedScreen) {
            self.markedPhone = index;
            self.enablePhonebtn = false;
            self.updatesphonescreen = selectedScreen;
            $timeout(function () {
                $scope.$apply();
            }, 100)

        }

        self.utscreen = function () {
            if (self.updatesscreen) {
                $cookieStore.put('back', "'/en-US/activity/create'");
                self.activityInfo = self.activity;
                ActivityInfo.setActivtyInformantion(self.activityInfo);
                if (self.updatesscreen) {
                    ActivityInfo.setDeviceTypes(self.updatesscreen);
                } else {
                }

                $location.url('/en-US/axe/tablet/update/');
            } else {
            }
        };
        self.upscreen = function () {
            if (self.updatesphonescreen) {
                $cookieStore.put('back', "'/en-US/activity/create'");
                self.activityInfo = self.activity;
                ActivityInfo.setActivtyInformantion(self.activityInfo);
                if (self.updatesphonescreen) {
                    ActivityInfo.setDeviceTypes(self.updatesphonescreen);
                } else {
                }

                $location.url('/en-US/axe/mobile/update/');
            } else {
            }
        };

        self.ctscreen = function () {
            if (self.selectTabletDevice) {
                $cookieStore.put('back', "'/en-US/activity/create'");
                self.activityInfo = self.activity;
                ActivityInfo.setActivtyInformantion(self.activityInfo);
                if (self.selectTabletDevice) {
                    ActivityInfo.setDeviceTypes(self.selectTabletDevice);
                }
                $location.url('/en-US/axe/tablet/create/');
            }
        }
        self.cpscreen = function () {
            if (self.selectPhoneDevice) {
                $cookieStore.put('back', "'/en-US/activity/create'");
                self.activityInfo = self.activity;
                ActivityInfo.setActivtyInformantion(self.activityInfo);
                if (self.selectPhoneDevice) {
                    ActivityInfo.setDeviceTypes(self.selectPhoneDevice);
                }
                $location.url('/en-US/axe/mobile/create/');
            }
        }

        self.createNoun = function () {
            $cookieStore.put('back', "'/en-US/activity/create'");

            var test = $cookieStore.get('back');
            $cookieStore.put('activity', JSON.stringify(self.activity));
            $location.url('/en-US/noun/create');
        };

        self.accordianPanel = function (accordian) {
            if (self.accordian == accordian) {
                self.accordian = 0;
            } else {
                self.accordian = accordian;
            }
        };
        self.sAccordianPanel = function (saccordian) {
            if (self.saccordian == saccordian) {
                self.saccordian = 0;
            } else {
                self.saccordian = saccordian;
            }
        };

        self.createActivity = function () {
            $cookieStore.remove('back');
            //self.activity.notes = self.richEditor.getHTML();
            self.activity.projectid = ActivityInfo.projectId;
            if (self.activity.name != null) {
                self.disablecreate = true;
                $http({
                    url: RestURL.baseURL + 'activity/create_activity/',
                    data: angular.toJson(self.activity),
                    method: 'POST',
                    headers: {
                        'Accept': 'application/json'
                    }
                })
                    .success(function (response) {
                        if (response != null) {
                            self.activity = response;
                            //self.richEditor.setHTML(self.activity.notes);
                            self.showCreate = false;
                            self.showupdatebtn = false;
                            self.successMessage = "Activity has been created successfully!";
                            self.toggleSuccessMessage('show');

                        }
                    })
                    .error(function (response) {
                    });
            }
        };


        self.toggleSuccessMessage = function (input) {
            $('#activitySuccess').modal(input);
        };

        self.updateActivity = function () {
            //self.activity.notes = self.richEditor.getHTML();
            if (self.activity.id != 0 && self.activity.id != null) {
                $http({
                    url: RestURL.baseURL + 'activity/update_activity/',
                    data: angular.toJson(self.activity),
                    method: 'POST',
                    headers: {
                        'Accept': 'application/json'
                    }
                })
                    .success(function (response) {
                        $location.url('/en-US/project/update/');
                    })
                    .error(function (response) {
                    });
            }
        };

        self.cancelActivity = function () {
            $location.url('/en-US/project/update/');
        };

        self.deleteActivity = function () {
            if (self.activity.id != 0 && self.activity.id != null) {
                $http({
                    url: RestURL.baseURL + 'activity/delete_activity/',
                    data: angular.toJson(self.activity),
                    method: 'POST',
                    headers: {
                        'Accept': 'application/json'
                    }
                })
                    .success(function (response) {
                        $location.url('/en-US/project/update/');
                    })
                    .error(function (response) {
                    });
            }
        }

        self.snoun = false;
        $scope.$watch('secondary_nouns', function (newValue, oldValue) {
            $log.log('new value : ', newValue, 'old value : ', oldValue);
            if (self.snoun) {
                if (newValue != null && newValue.id != 0) {
                    var alreadyExist = true;
                    var indx = self.activity.secondary_nouns.indexOf(newValue);
                    for (var i = 0; i < self.activity.secondary_nouns.length; i++) {
                        if (newValue.id == self.activity.secondary_nouns[i].id) {
                            alreadyExist = false;
                        }
                    }
                    if (indx == -1 && alreadyExist) {
                        self.activity.secondary_nouns.push(newValue);
                        indx = self.activity.secondary_nouns.indexOf(newValue);
                        var toremoveindx = self.secondaryNouns.indexOf(newValue)
                        self.secondaryNouns.splice(toremoveindx, 1);
                    }
                    var len = self.primaryNouns.length;
                    var idx = 0;
                    for (var i = 0; i < len; i++) {
                        if (newValue.id == self.primaryNouns[i].id) {
                            idx = i;
                        }
                    }
                    self.primaryNouns.splice(idx, 1);
                }
            } else {
                self.snoun = true;
            }
        });

        self.pnoun = false;
        $scope.$watch('activity.primary_noun', function (newValue, oldValue) {
            $log.log('new value : ', newValue, 'old value : ', oldValue);
            if (self.pnoun) {
                if (newValue != null) {
                    var len = self.secondaryNouns.length;
                    var idx = self.secondaryNouns.indexOf(newValue);
                    var idx = 0;
                    for (var i = 0; i < len; i++) {
                        if (newValue.id == self.secondaryNouns[i].id) {
                            idx = i;
                        }
                    }
                    self.secondaryNouns.splice(idx, 1);
                }
                var isExist = false;
                if (oldValue != null && !jQuery.isEmptyObject(oldValue)) {
                    for (var i = 0; i < self.secondaryNouns.length; i++) {
                        if (self.secondaryNouns[i].id === oldValue.id) {
                            isExist = true;
                        }
                    }
                    if (isExist) {
                    } else if (oldValue.id != 0 && oldValue != null) {
                        self.secondaryNouns.push(oldValue);
                    }
                }
            } else {
                self.pnoun = true;
            }
        });
        self.stablet = false;
        $scope.$watch('selectTabletDevice', function (newValue, oldValue) {
            if (self.stablet) {
                if (newValue != null) {
                    self.enablecreateTabletbtn = false;
                } else {
                    self.enablecreateTabletbtn = true;
                }
            } else {
                self.stablet = true;
            }
        });
        self.stablet = false;
        $scope.$watch('selectPhoneDevice', function (newValue, oldValue) {
            if (self.stablet) {
                if (newValue != null) {
                    self.enablecreatePhonebtn = false;
                } else {
                    self.enablecreatePhonebtn = true;
                }
            } else {
                self.stablet = true;
            }
        });
        //Created noun from Noun Screen
        self.updateCeratedNoun = function () {
            var noun = ActivityInfo.noun;
            self.updatePrimaryNoun(noun);
            ActivityInfo.setNoun({});
            $timeout(function () {
                $scope.$apply();
            }, 100);
        };
        self.updatePrimaryNoun = function (noun) {
            if (noun != undefined || !jQuery.isEmptyObject(noun)) {
                var len = self.primaryNouns.length;
                var idx = 0;
                for (var i = 0; i < len; i++) {
                    if (noun.id == self.primaryNouns[i].id) {
                        idx = i;
                        break;
                    }
                }
                self.activity.primary_noun = self.primaryNouns[idx];
            }
        };
        //Remove Primary noun from secondary && Remove secondary noun from primary
        self.removeRepeatedNouns = function () {

            $timeout(function () {
                var b = self.primaryNouns;
                var a = self.activity.secondary_nouns;
                if (self.activity.secondary_nouns != null) {
                    for (var i = 0, len = a.length; i < len; i++) {
                        for (var j = 0, len2 = b.length; j < len2; j++) {
                            if (a[i].id === b[j].id) {
                                b.splice(j, 1);
                                len2 = b.length;
                                break;
                            }
                        }
                    }
                }
                //Secondary noun drop down
                var bc = self.secondaryNouns;
                var ac = self.activity.primary_noun;
                if (self.activity.secondary_nouns != null) {
                    for (var i = 0, len = a.length; i < len; i++) {
                        for (var j = 0, len2 = bc.length; j < len2; j++) {
                            if (a[i].id === bc[j].id) {
                                bc.splice(j, 1);
                                len2 = bc.length;
                                break;
                            }
                        }
                    }
                }
                if (self.activity.primary_noun != null) {
                    for (var j = 0, len2 = bc.length; j < len2; j++) {
                        if (ac.id === bc[j].id) {
                            bc.splice(j, 1);
                            len2 = bc.length;
                            break;
                        }
                    }
                }

            }, 500);
        };

    }]);