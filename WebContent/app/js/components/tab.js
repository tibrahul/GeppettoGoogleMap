$(document).on('click', '.tab._Gp_element .tab-list li a', function (e) {
    e.preventDefault();

    var panel = $(this).data('ng-href');
    $(this).parent('li').addClass('active').siblings().removeClass('active');
    $('.tab .tab-content ' + panel).addClass('active').siblings().removeClass('active');

    /* show children of current panel and hide siblings children */
    var screenChildren = nodeTree['screen']['children'];
    var tab = $(panel).parents('.resizable').attr('id');
    var firstLevel, secondLevel;

    if (screenChildren.hasOwnProperty(tab)) {
        if (screenChildren[tab].hasOwnProperty('children')) {
            for (firstLevel in screenChildren[tab]['children']) {
                if (screenChildren[tab]['children'].hasOwnProperty(firstLevel)) {
                    if (screenChildren[tab]['children'][firstLevel].hasOwnProperty('children')) {
                        if (firstLevel === panel.substr(1)) {
                            for (secondLevel in screenChildren[tab]['children'][firstLevel]['children']) {
                                if (screenChildren[tab]['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {
                                    $('#' + secondLevel).show();
                                }
                            }
                        } else {
                            for (secondLevel in screenChildren[tab]['children'][firstLevel]['children']) {
                                if (screenChildren[tab]['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {
                                    $('#' + secondLevel).hide();
                                }
                            }
                        }
                    }
                }
            }
        }
    }
});