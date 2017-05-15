app.factory('ProjectData', function () {
    return {
        project_data: {},
        setData: function (data) {
            this.project_data = data;
        }
    };
});