var gulp = require('gulp');
var del = require('del');

gulp.task('clean', function (cb) {
    del(['./dist'], cb);
});

// gulp.task('clean:target', function () {
//     del([
//         '../*.js',
//         '../*.css',
//         '../*.ico',
//         '../*.html'
//     ]);
// });

// gulp.task('copy:files', function (cb) {
//     gulp.src([
//             'dist/*.js',
//             'dist/*.css',
//             'dist/*.ico',
//             'dist/*.html'
//         ],
//         {base: 'dist'}).pipe(gulp.dest('target/'))
// })

