package by.sideproject.videocaster.app.rest.routes.video.handlers

import akka.actor.Actor
import by.sideproject.videocaster.app.rest.routes.video.requests.{VideosAddRequest, VideosGetEntitiesRequest}
import by.sideproject.videocaster.services.downloader.base.DownloadService
import by.sideproject.videocaster.services.storage.base.dao.VideoItemDetailsDAO
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext

class VideosAddHandler(videoDetailsDAO: VideoItemDetailsDAO, downloadService: DownloadService)
                      (implicit executionContext: ExecutionContext) extends Actor {

  import by.sideproject.videocaster.app.rest.formaters.json.InstaVideoJsonProtocol._
  import spray.httpx.SprayJsonSupport.{sprayJsonMarshaller, sprayJsonUnmarshaller}

  val log = LoggerFactory.getLogger(this.getClass)

  override def receive = {
    case VideosAddRequest(ctx, request, user) => {
      log.debug(s"Processing add video request: $request")

      val videoDetailsResponse = downloadService.getVideoDetails(request.baseUrl)

      videoDetailsResponse.fold(
        error => ctx.complete(error),
        videoItemDetails => {

          log.debug("Saving newly created video item: " + videoItemDetails)

          val insertionResult = for {
            videoItemID <- videoDetailsDAO.insert(videoItemDetails)
            addedVideoItemEntry <- videoDetailsDAO.findOneById(videoItemID)
          } yield {
            log.debug("Initiating download of video file for: " + videoItemDetails)
            addedVideoItemEntry.map(downloadService.download(_, user))

            addedVideoItemEntry
          }

          ctx.complete(insertionResult)
        })


    }
  }
}
