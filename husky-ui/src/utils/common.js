const getItemByKey = (key, arr, keyName) => {
    if (arr && arr.length > 0) {
        for (var i = 0; i < arr.length; i++) {
            let item = arr[i];
            if (item[keyName] === key) {
                return item
            }
        }
    }
}

const initBreadcrumbDatas = (menuId, menus, datas) => {
    let curMenu = getItemByKey(menuId, menus, 'id');
    if (curMenu) {
        datas.unshift({
            path: curMenu.href,
            title: curMenu.name
        });
        initBreadcrumbDatas(curMenu.parentId, menus, datas);
    }
    return datas
}

const getTreeData = (menus) => {
    let nodes = [];
    if (menus && menus.length > 0) {
        // 构造parentId为key的obj
        let obj = {}
        menus.forEach(node => {
            // 注意地址引用            
            if (!obj[node.parentId]) {
                obj[node.parentId] = [];
            }
            obj[node.parentId].push(node);
        });
        Object.assign(nodes, obj[0]);
        setChildren(nodes, obj);
    }
    return nodes;
}

const setChildren = (nodes, obj) => {
    if (nodes) {
        nodes.forEach(node => {
            if (!node.children) node.children = [];
            let nextNodes = obj[node.id];
            Object.assign(node.children, nextNodes);
            setChildren(node.children, obj);
        })
    }
}

export {
    getItemByKey,
    initBreadcrumbDatas,
    getTreeData
}