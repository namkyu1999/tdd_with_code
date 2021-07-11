import { useState } from "react";
import CommentList from "./CommentList";
import { Form } from "./Form";

function App({ commentComposer }) {
  const [comments, setComments] = useState([]);

  return (
    <div>
      <Form commentComposer={commentComposer, onNewComment} />
      <CommentList comments={comments} />
    </div>
  );

  function onNewComment(newComment) {
    setComments([...[...comments], newComment]);
  }
}
export default App;
