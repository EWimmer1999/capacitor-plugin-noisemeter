export interface NoiseMeterPlugin {

  start():Promise<void>
  startRecording(): Promise<void>
  stop(): Promise<void>
  getNoiseLevel(): Promise<{decibels: number}>

}

