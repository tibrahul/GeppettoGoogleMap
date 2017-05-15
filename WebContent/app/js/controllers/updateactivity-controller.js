// Date Created: March 27 2015
// @author Vijay
// Purpose of this controller to update the existing noun and nounattributes.
// since .75

app.controller('UpdateActivityController', ['$log', '$scope', '$http', '$timeout', '$location', '$cookieStore', 'RestURL', 'ActivityInformation',
    function ($log, $scope, $http, $timeout, $location, $cookieStore, RestURL, ActivityInfo) {
        var self = $scope;
        self.activity = {
            phone_screens: [],
            tablet_screens: [],
            notes: null
        };
        self.markedRow = -1;
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
        self.activityInfo = {
            id: 0,
            primary_noun: {},
            secondary_nouns: [],
        };


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
            $log.log('Activity Id :  ', ActivityInfo.activityId);
            $log.log('Project Id : ', ActivityInfo.projectId);

            $timeout(function () {
                //Initializing text editor (Quill)
                /*self.richEditor = new Quill( '#_editor_rte', {
                 theme : 'snow',
                 modules : {
                 'link-tooltip' : true,
                 'toolbar': {
                 container: '#_toolbar_rte'
                 }
                 }
                 });*/
            }, 100);
            self.showCreate = true;
            self.disablecreate = false;
            self.accordian = 1;
            self.saccordian = 2;

            $http.get(RestURL.baseURL + 'activity/search_for_update/?activity_id=' + ActivityInfo.activityId)
                .success(function (response) {
                    self.allActivity = response;
                    $log.log('Activity Info', JSON.stringify(response));
                    angular.copy(self.allActivity, self.activity);
                    /*	$timeout( function(){
                     if( self.activity.notes != null ){
                     self.richEditor.setHTML(self.activity.notes);
                     }
                     },100);*/
                })
                .error(function (response) {
                });

            $http.get(RestURL.baseURL + 'noun/get_all_nouns_by_project_id/?project_id=' + ActivityInfo.projectId)
                .success(function (response) {
                    self.allNounProject = response;
                    if (self.allNounProject != null) {
                        angular.copy(self.allNounProject, self.primaryNouns);
                        angular.copy(self.allNounProject, self.secondaryNouns);

                        //Update only when back from create noun screen
                        var backUrl = $cookieStore.get('back');
                        if (backUrl == "'/en-US/noun/create'") {
                            self.updateCreatedNoun();
                        }
                        else if (backUrl == "'/en-US/axe/mobile/create/'" || backUrl == "'/en-US/axe/mobile/update/'"
                            || backUrl == "/en-US/axe/tablet/create/" || backUrl == "/en-US/axe/tablet/update/") {
                            //This for to set selected Object Of noun
                            self.updatePrimaryNoun(self.activity.primary_noun);
                        }
                    } else {
                        self.primaryNouns = [];
                        self.secondaryNouns = [];
                    }

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
                        }
                    }
                })
                .error(function (response) {
                });


            self.getProjectInfo();


        };

        /**
         * goto project
         */
        self.gotoProject = function () {
            $cookieStore.put('back', "'/en-US/activity/update'");
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


        self.init();
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

        self.createNoun = function () {
            $cookieStore.put('back', "'/en-US/activity/update'");
            self.activityInfo = self.activity;
            ActivityInfo.setActivtyInformantion(self.activityInfo);
            $cookieStore.put('activity', JSON.stringify(self.activity));
            $location.url('/en-US/noun/create');
        };
        self.snoun = false;
        $scope.$watch('secondary_nouns', function (newValue, oldValue) {
            if (self.snoun) {
                if (newValue != null) {
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
                        var toremoveindx = self.secondaryNouns.indexOf(newValue);
                    }
                    //Removing value from the list
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
                        $log.log('Already Exists; !');
                    } else if (oldValue.id != 0 && oldValue != null) {
                        self.secondaryNouns.push(oldValue);
                    }

                }
            } else {
                self.pnoun = true;
            }
        });
        self.cancelActivity = function () {
            $location.url('/en-US/project/update/');
        }

        self.updateActivity = function () {
            //	self.activity.notes = self.richEditor.getHTML();
            $http({
                url: RestURL.baseURL + 'activity/update_activity/',
                data: angular.toJson(self.activity),
                method: 'POST',
                headers: {
                    'Accept': 'application/json'
                }
            })
                .success(function (response) {
                    $cookieStore.remove('back');
                    $location.url('/en-US/project/update/');
                })
                .error(function (response) {
                });
        };

        self.deleteActivity = function () {
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
        self.utscreen = function () {
            $('#tblt-view').modal('hide');
            $('.modal-backdrop').remove();
            $("body").removeClass("modal-open");
            if (self.updatesscreen) {
                $cookieStore.put('back', "'/en-US/activity/update'");
                self.activityInfo = self.activity;
                ActivityInfo.setActivtyInformantion(self.activityInfo);
                if (self.updatesscreen) {
                    ActivityInfo.setDeviceTypes(self.updatesscreen);
                }
                $timeout(function () {
                    $location.url('/en-US/axe/tablet/update/');
                }, 100);
            }
        }
        self.upscreen = function () {

            $('#mbl-view').modal('hide');
            $('.modal-backdrop').remove();
            $("body").removeClass("modal-open");
            if (self.updatesphonescreen) {

                $cookieStore.put('back', "'/en-US/activity/update'");
                self.activityInfo = self.activity;
                ActivityInfo.setActivtyInformantion(self.activityInfo);
                if (self.updatesphonescreen) {
                    ActivityInfo.setDeviceTypes(self.updatesphonescreen);
                }
                $timeout(function () {
                    $location.url('/en-US/axe/mobile/update/');
                }, 100);
            }
        }

        self.ctscreen = function () {

            $('#tblt-view').modal('hide');
            $('.modal-backdrop').remove();
            $("body").removeClass("modal-open");
            if (self.selectTabletDevice) {
                $cookieStore.put('back', "'/en-US/activity/update'");
                self.activityInfo = self.activity;
                ActivityInfo.setActivtyInformantion(self.activityInfo);
                if (self.selectTabletDevice) {
                    ActivityInfo.setDeviceTypes(self.selectTabletDevice);
                }
                $timeout(function () {
                    $location.url('/en-US/axe/tablet/create/');
                }, 100);
            }
        }
        self.cpscreen = function () {
            $('#mbl-view').modal('hide');
            $('.modal-backdrop').remove();
            $("body").removeClass("modal-open");
            if (self.selectPhoneDevice) {
                $cookieStore.put('back', "'/en-US/activity/update'");
                self.activityInfo = self.activity;
                ActivityInfo.setActivtyInformantion(self.activityInfo);
                if (self.selectPhoneDevice) {
                    ActivityInfo.setDeviceTypes(self.selectPhoneDevice);
                }

                $timeout(function () {
                    $location.url('/en-US/axe/mobile/create/');
                }, 100);
            }
        }

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
//Created noun from Noun Screen
        self.updateCreatedNoun = function () {
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


        /**
         * toggling success message
         */
        self.toggleSuccessMessage = function (input) {
            $('#activitySuccess').modal(input);
        };

    }]);