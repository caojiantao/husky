const getToken = () => localStorage.getItem('token')

const saveToken = (token) => {
    window.localStorage.setItem('token', token)
}

const initMenusAndRoutes = (userId, store, _axios, router) => {
    // 获取当前用户的菜单信息
    _axios
        .get("/system/menu/getMenusByUserId", {
            params: {
                userId: userId
            }
        })
        .then(menus => {
            // 初始化菜单，以及动态添加路由
            store.commit("setMenus", menus);
            let routes = [];
            for (let index in menus) {
                let curItem = menus[index];
                if (curItem.href) {
                    routes.push({
                        path: curItem.href,
                        name: curItem.name,
                        // 坑！！！必须带上文件夹
                        component: resolve => require(["@/" + curItem.componentPath], resolve)
                    });
                }
            }
            // 根据后端返回菜单，动态添加路由
            router.addRoutes([{
                path: "/",
                name: "Home",
                component: () => import("@/views/Home.vue"),
                children: routes
            }]);
        })
        .catch(() => {});
}

const getBreadcrumbDatas = (menuId, menus) => {
    let datas = []
    if (menus && menus.length > 0) {
        // 构造ID-MENU映射
        let obj = {}
        menus.forEach(item => {
            obj[item.id] = item;
        });
        let curMenu;
        while ((curMenu = obj[menuId])) {
            datas.unshift({
                path: curMenu.href,
                title: curMenu.name
            });
            menuId = curMenu.parentId;
        }
    }
    return datas
}

export {
    getToken,
    saveToken,
    initMenusAndRoutes,
    getBreadcrumbDatas
}