const loader = {
    show: () => {
        $('#page-loader').show();
    },
    hide: () => {
        $('#page-loader').hide();
    },
};

const URLS = {
    recipes: '/api/recipes',
};

const toString = ({ id, name, difficulty, taste, known }) => {
    let columns = `
    <td>${name}</td>
    <td>${difficulty}</td>
    <td>${taste}</td>`
    columns += known
        ? '<td></td>'
        : `<td>
            <form class="add-recipe-form" data-id=${id} action="/api/recipes/${id}" method="post">
                <button class="btn btn-info">Learn</button>
            </form>
           </td>`
    return `<tr>${columns}</tr>`
};

loader.show();
fetch(URLS.recipes)
    .then(response => response.json())
    .then(recipes => {
        let result = '';
        recipes.forEach(recipe => {
            const recipeString = toString(recipe);
            result += recipeString;
        });

        $('#recipes-table').html(result);
        loader.hide();
    });

$('#recipes-table').on('submit', '.add-recipe-form', function (ev) {
    const url = $(this).attr('action');

    loader.show();
    fetch(url, { method: 'post' })
        .then(data => {
            console.log(data)
            loader.hide();
        });

    ev.preventDefault();
    return false;
});